import { useEffect, useState } from 'react';
import { updateCourse, getCourseById } from "../../api/courseApi";
import { useParams, useNavigate } from 'react-router-dom';

function UpdateCourse() {
  const { id } = useParams();
  const [course, setCourse] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    async function fetchCourse() {
      try {
        const data = await getCourseById(id);
        setCourse(data);
      } catch (error) {
        console.error("Cours introuvable avec l'id :", id);
      }
    }

    fetchCourse();
  }, [id]);

  const handleChange = (e) => {
    setCourse({ ...course, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await updateCourse(course, id);
      navigate('/');
    } catch (error) {
      console.error("Erreur lors de la modification :", error);
    }
  };

  if (!course) return <p className="text-center mt-8">Chargement...</p>;

  return (
    <div className="max-w-md mx-auto mt-8 p-4 bg-white rounded shadow">
      <h2 className="text-xl font-bold mb-4 text-center">Modifier un cours</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <input
          name="courseName"
          placeholder="Nom"
          className="w-full p-2 border"
          onChange={handleChange}
          value={course.courseName || ''}
        />
        <input
          name="courseDescription"
          placeholder="Description"
          className="w-full p-2 border"
          onChange={handleChange}
          value={course.courseDescription || ''}
        />
        <input
          name="category"
          placeholder="Catégorie"
          className="w-full p-2 border"
          onChange={handleChange}
          value={course.category || ''}
        />
        <input
          name="instructor"
          placeholder="Instructeur"
          className="w-full p-2 border"
          onChange={handleChange}
          value={course.instructor || ''}
        />
        <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded">Modifier</button>
      </form>
    </div>
  );
}

export default UpdateCourse;
