import { useState } from 'react';
import { addCourse } from '../../api/courseApi';
import { useNavigate } from 'react-router-dom';

function AddCourse() {
  const navigate = useNavigate();
  const [course, setCourse] = useState({
    courseName: '',
    courseDescription: '',
    category: '',
    instructor: '',
  });

  const handleChange = (e) => {
    setCourse({ ...course, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try{
        await addCourse(course);
        navigate('/');
    }catch(error){
        console.error("Erreur lors de l'ajout :", error);
    }
      
  };

  return (
    <div className="max-w-md mx-auto mt-8 p-4 bg-white rounded shadow">
      <h2 className="text-xl font-bold mb-4 text-center">Ajouter un cours</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <input name="courseName" placeholder="Nom" className="w-full p-2 border" onChange={handleChange} />
        <input name="courseDescription" placeholder="Description" className="w-full p-2 border" onChange={handleChange} />
        <input name="category" placeholder="Catégorie" className="w-full p-2 border" onChange={handleChange} />
        <input name="instructor" placeholder="Instructeur" className="w-full p-2 border" onChange={handleChange} />
        <button type="submit" className="bg-blue-600 text-white px-4 py-2 rounded">Ajouter</button>
      </form>
    </div>
  );
}

export default AddCourse;
