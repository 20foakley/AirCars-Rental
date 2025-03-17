import React from 'react'
import {Link} from 'react-router-dom'
import {useState} from "react";
import {FaMapMarker} from 'react-icons/fa'

const CarListing = ({car}) => {

    // needs to have make, model, year
    // picture, description, user
    // comments eventually?

    <div className='justify-items-center w:50%'>
        {/* align center to screen */}

        <img>
            {/* listing image */}
        </img>

        <div>
            {/* container that says title of car */}
        </div>
        <div>
            {/* container that hays number of stars of user */}
        </div>
        <div>
            {/* container that has trip start, trip end */}
        </div>
        <div>
            {/* container that shows the lister's possible pickup and return preferences */}
        </div>
        <div>
            {/* container that shows distance included */}
        </div>
        <div>
            {/* container that shows insurance type */}
        </div>
        {/* container that shows mpg, num doors, gas, num seats */}
        <div>
            HOSTED BY (numtrips, when they joined)
        </div>
        <div>
            {/* description */}
        </div>
        <div>
            {/* all the features */}
        </div>
        <div>
            ratings of the user
        </div>
        <div>google map/openstreetmap with the pickup location and some alternate pickups</div>

        <div>element that follows bottom of screen with price per day and Continue</div>

        <div> eventual options to add to favs, report listing, cancellation, and share listing</div>


    </div>
}
export default CarListing;