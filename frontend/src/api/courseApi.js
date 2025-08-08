const BASE_URL = import.meta.env.VITE_API_BASE_URL;
export async function getCourses() {
  const response = await fetch(`${BASE_URL}/api/courses/all`);
  const text = await response.text();

  try {
    return JSON.parse(text);
  } catch (e) {
    console.error("Erreur JSON :", e);
    throw new Error("La réponse du serveur n'est pas un JSON valide.");
  }
}
 export async function addCourse(courseData) {
 const response = await fetch(`${BASE_URL}/api/courses`,{
  method :'post',
  headers:{
    'Content-Type':'application/json'
  },
  body:JSON.stringify(courseData)
 }); 
  if(!response.ok){
    throw new Error("Erreur lors de l'ajout du cours");
  
  }
}
 export async function updateCourse (courseUpdated,id) 
  {
    const response = await fetch(`${BASE_URL}/api/courses/${id}`,{
      method:'PUT',
      headers:{
        'Content-Type':'application/json'
      },
      body:JSON.stringify(courseUpdated)
    });
    if(!response.ok){
    throw new Error("Erreur lors de la modification du cours");
    }
  }
   export async function getCourseById (id) 
  {
  const response = await fetch(`${BASE_URL}/api/courses/${id}`);

  if (!response.ok) {
    throw new Error("Erreur lors de la récupération du cours");
  }
  return await response.json();
  }

     export async function deleteCourse (id) 
  {
  const response = await fetch(`${BASE_URL}/api/courses/${id}`,{
    method:'DELETE'
  });

  if (!response.ok) {
    throw new Error("Erreur lors de la récupération du cours");
  }
  return response.status;
  }
export async function searchCourses(keyword){
  const response = await fetch(`${BASE_URL}/api/courses/search?keyword=${encodeURIComponent(keyword)}`);
  if (!response.ok) throw new Error("Erreur lors de la recherche");
  return await response.json();
  } 

