import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Walmart from './Components/Pages/Walmart/Walmart'

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<Walmart/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
