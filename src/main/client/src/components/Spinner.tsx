import React from 'react'
import ClipLoader from 'react-spinners/Cliploader'

// not currently implemented for loading - on todo list

const override = {
    display: 'block',
    margin: '100px auto'

}

const Spinner = ({loading}) => {
  return (
    <ClipLoader
        color = '#4338ca'
        loading={loading}
        cssOverride = {override}
        size={150}
    />
  )
}

export default Spinner