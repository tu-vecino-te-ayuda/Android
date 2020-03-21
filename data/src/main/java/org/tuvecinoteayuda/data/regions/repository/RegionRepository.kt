package org.tuvecinoteayuda.data.regions.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.tuvecinoteayuda.data.DataContextWrapper
import org.tuvecinoteayuda.data.getJsonDataFromAsset
import org.tuvecinoteayuda.data.regions.models.Region

class RegionRepository(private val contextWrapper: DataContextWrapper) {

    fun getRegions(): List<Region> {
        val fileData = getJsonDataFromAsset(contextWrapper.context, REGIONS)
        val regionsListType = object : TypeToken<Array<Region>>() {}.type
        return Gson().fromJson(fileData, regionsListType)
    }

    companion object {
        private const val REGIONS: String = "regions.json"

        fun newInstance(): RegionRepository {
            return RegionRepository(DataContextWrapper)
        }
    }
}