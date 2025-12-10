import React from 'react'
import {useAuth} from '../context/AuthContext'


const ListPage = () => {
  const {user} = useAuth();
  return (
    <div>ListPage</div>
  )
}

export default ListPage