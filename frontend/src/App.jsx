import { Routes, Route } from 'react-router-dom';
import CourseList from './pages/Course/CourseList';
import AddCourse from './pages/Course/AddCourse';
import UpdateCourse from './pages/Course/UpdateCourse';
import Navbar from './components/Navbar';

function App() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<CourseList />} />
        <Route path="/courses/new" element={<AddCourse />} />
        <Route path="/courses/edit/:id" element={<UpdateCourse />} />
      </Routes>
    </>
  );
}

export default App;
