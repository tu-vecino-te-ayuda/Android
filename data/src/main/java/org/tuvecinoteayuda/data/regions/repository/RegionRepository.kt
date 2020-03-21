package org.tuvecinoteayuda.data.regions.repository

import com.google.gson.Gson
import org.tuvecinoteayuda.data.regions.models.Region

class RegionRepository {

    fun getRegions(): List<Region>{
        list = Gson().fromJson(,)
        return
    }

    //TODO MOVE THIS TO A FILE
    companion object {
         private const val  REGIONS =
    }
}