import { useState } from 'react'

import './index.css'
import Navbar from './page/home/Navbar/Navbar'
import Home from './page/home/Home'
import TestNavbar from './page/home/Navbar/TestNavbar'
function App() {
  const [count, setCount] = useState(0)

  return (
    <>
    <Navbar/>
    <Home/>
    </>
  )
}

export default App
