import { MemoryRouter as Router, Routes, Route, BrowserRouter } from 'react-router-dom';
import './App.css';
import PlantSearchForm from '../app/components/add_plants/plant_search_form';
import Header from '../app/components/header';

function Landing() {
  return (
    <div>
      <h1>Plant Management</h1>
      <p>This is a software meant to manage the plants you have around your house and all their different needs.</p>
      <p>Copyright and license stuff here.</p>
    </div>
  )
}

function Search() {
  return (
    <div>
      <h1>Plant Search</h1>

      <PlantSearchForm></PlantSearchForm>
    </div>
  );
}

export default function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<Landing />} />
        <Route path="/search" element={<Search />} />
      </Routes>
    </Router>
  );
}
