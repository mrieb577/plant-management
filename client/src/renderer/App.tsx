import { MemoryRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import PlantSearchForm from '../app/components/plant_search_form';

function Hello() {

  return (
    <div>
      <h1>Plant Management</h1>

      <PlantSearchForm></PlantSearchForm>
    </div>
  );
}

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Hello />} />
      </Routes>
    </Router>
  );
}
