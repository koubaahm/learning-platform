import { useEffect, useRef, useState } from 'react';
import { getCourses, deleteCourse, searchCourses } from '../../api/courseApi';
import { useNavigate, useSearchParams } from 'react-router-dom';
import Swal from 'sweetalert2';
import useDebounce from '../../hooks/useDebounce';

function CourseList() {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [errMsg, setErrMsg] = useState('');
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();

  const initialQ = (searchParams.get('q') || '').trim();
  const [q, setQ] = useState(initialQ);
  const debounced = useDebounce(q, 350);

  const shouldFocus = searchParams.get('focus') === 'search';
  const inputRef = useRef(null);

  // Focus si demandé par la navbar
  useEffect(() => {
    if (shouldFocus) {
      inputRef.current?.focus();
      // on nettoie le paramètre focus pour garder une URL propre
      const sp = new URLSearchParams(searchParams);
      sp.delete('focus');
      setSearchParams(sp, { replace: true });
    }
  }, [shouldFocus, searchParams, setSearchParams]);

  // Navigation debouncée quand l’utilisateur tape
  useEffect(() => {
    const query = debounced.trim();
    const target = query ? `/?q=${encodeURIComponent(query)}` : '/';
    const current = window.location.pathname + window.location.search;
    if (current !== target) {
      navigate(target, { replace: true });
    }
  }, [debounced, navigate]);

  // Chargement des données selon q (URL source de vérité)
  useEffect(() => {
    let cancelled = false;
    (async () => {
      setLoading(true);
      setErrMsg('');
      try {
        const urlQ = (searchParams.get('q') || '').trim();
        const data = urlQ ? await searchCourses(urlQ) : await getCourses();
        if (!cancelled) setCourses(data);
      } catch (err) {
        if (!cancelled) setErrMsg('Une erreur est survenue lors du chargement.');
        console.error(err);
      } finally {
        if (!cancelled) setLoading(false);
      }
    })();
    return () => { cancelled = true; };
  }, [searchParams]);

  const handleAdd = () => navigate('/courses/new');
  const handleEdit = (course) => navigate(`/courses/edit/${course.id}`);

  const handleDelete = async (id) => {
    const result = await Swal.fire({
      title: 'Supprimer ce cours ?',
      text: 'Cette action est irréversible.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#e3342f',
      cancelButtonColor: '#6c757d',
      confirmButtonText: 'Supprimer',
      cancelButtonText: 'Annuler',
    });

    if (result.isConfirmed) {
      try {
        await deleteCourse(id);
        const currentQ = (searchParams.get('q') || '').trim();
        const updated = currentQ ? await searchCourses(currentQ) : await getCourses();
        setCourses(updated);
        Swal.fire({ title: 'Supprimé !', text: 'Le cours a bien été supprimé.', icon: 'success', timer: 2000, showConfirmButton: false });
      } catch {
        Swal.fire('Erreur', 'Impossible de supprimer le cours.', 'error');
      }
    }
  };

  const urlQ = (searchParams.get('q') || '').trim();

  return (
    <div className="max-w-md mx-auto mt-8 p-4 bg-gray-100 rounded">
      <h1 className="text-xl font-bold text-center mb-4">Liste des cours</h1>

      {/* Champ de recherche local */}
      <div className="mb-4 flex items-center gap-2">
        <input
          ref={inputRef}
          type="search"
          value={q}
          onChange={(e) => setQ(e.target.value)}
          placeholder="Rechercher un cours…"
          className="flex-1 rounded px-3 py-2 text-sm outline-none bg-white focus:bg-white"
        />
        {urlQ && (
          <button
            onClick={() => { setQ(''); navigate('/', { replace: true }); }}
            className="text-sm px-3 py-2 rounded bg-gray-200 hover:bg-gray-300"
          >
            Effacer
          </button>
        )}
      </div>

      {urlQ && (
        <div className="mb-3 text-sm text-gray-600">
          Résultats pour <span className="font-semibold">“{urlQ}”</span>
        </div>
      )}

      <button
        onClick={handleAdd}
        className="mb-4 w-full bg-blue-600 hover:bg-blue-700 text-white py-2 px-4 rounded"
      >
        Ajouter un cours
      </button>

      {loading && (
        <ul>
          {[...Array(3)].map((_, i) => (
            <li key={i} className="mb-3 p-3 bg-white rounded shadow animate-pulse">
              <div className="h-4 w-2/3 bg-gray-200 rounded mb-2" />
              <div className="h-3 w-1/3 bg-gray-200 rounded mb-2" />
              <div className="h-3 w-1/4 bg-gray-200 rounded" />
            </li>
          ))}
        </ul>
      )}

      {!loading && errMsg && (
        <div className="p-3 bg-red-100 text-red-800 rounded text-sm">{errMsg}</div>
      )}

      {!loading && !errMsg && courses.length === 0 && (
        <div className="p-4 text-center text-gray-600 bg-white rounded shadow">
          {urlQ ? <>Aucun cours ne correspond à “{urlQ}”.</> : <>Aucun cours pour le moment.</>}
        </div>
      )}

      {!loading && !errMsg && courses.length > 0 && (
        <ul>
          {courses.map((course) => (
            <li key={course.id} className="mb-3 p-3 bg-white rounded shadow text-gray-800">
              <p className="font-medium">{course.courseName}</p>
              <p className="text-sm text-gray-500">{course.category}</p>
              <p className="text-sm text-gray-500">
                {course.createdDate ? new Date(course.createdDate).toLocaleDateString() : '—'}
              </p>
              <div className="mt-2 flex justify-end space-x-2">
                <button
                  onClick={() => handleEdit(course)}
                  className="bg-yellow-500 hover:bg-yellow-600 text-white py-1 px-3 rounded text-sm"
                >
                  Modifier
                </button>
                <button
                  onClick={() => handleDelete(course.id)}
                  className="bg-red-600 hover:bg-red-700 text-white py-1 px-3 rounded text-sm"
                >
                  Supprimer
                </button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default CourseList;
