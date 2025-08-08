import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';

function Navbar() {
  const location = useLocation();
  const navigate = useNavigate();

  const navLinkClass = (path) =>
    `px-4 py-2 rounded hover:bg-blue-700 transition duration-300 ${
      location.pathname === path ? 'bg-blue-600 text-white' : 'text-gray-200'
    }`;

  return (
    <nav className="bg-blue-800 shadow-md">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center h-16">
          {/* Gauche : logo + liens */}
          <div className="flex items-center gap-4">
            <Link to="/" className="flex items-center space-x-2 hover:opacity-90 transition duration-300">
              <span className="text-2xl animate-bounce">📚</span>
              <span className="text-white text-xl font-bold">LearningPlatform</span>
            </Link>

            <Link to="/" className={navLinkClass('/')}>Accueil</Link>
            <Link to="/courses/new" className={navLinkClass('/courses/new')}>Ajouter un cours</Link>
          </div>

          {/* Droite : icône recherche → focus sur la page */}
          <button
            onClick={() => navigate('/?focus=search')}
            className="ml-auto hidden sm:inline-flex items-center justify-center w-9 h-9 rounded hover:bg-blue-700 text-white"
            aria-label="Rechercher un cours"
            title="Rechercher un cours"
          >
            🔍
          </button>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
