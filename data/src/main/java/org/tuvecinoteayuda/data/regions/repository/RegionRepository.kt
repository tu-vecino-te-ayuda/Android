package org.tuvecinoteayuda.data.regions.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.tuvecinoteayuda.data.DataContextWrapper
import org.tuvecinoteayuda.data.getJsonDataFromAsset
import org.tuvecinoteayuda.data.regions.models.City
import org.tuvecinoteayuda.data.regions.models.Region

class RegionRepository(private val contextWrapper: DataContextWrapper) {

    private var regions: List<Region>? = null

    fun getRegions(): List<Region> {
        val currentRegions = regions
        if (currentRegions != null) return currentRegions
        val fileData: String? = getJsonDataFromAsset(contextWrapper.context, REGIONS)
        val regionsListType = object : TypeToken<List<Region>>() {}.type
        return (fileData?.let { Gson().fromJson<List<Region>>(it, regionsListType) }
            ?: emptyList()).apply { regions = this }

    }

    fun getCitiesFromRegion(regionName: String): List<City> {
        return getRegions().firstOrNull { it.name == regionName }?.cities ?: listOf()
    }

    companion object {
        private const val REGIONS: String = "regions.json"

        fun newInstance(): RegionRepository {
            return RegionRepository(DataContextWrapper)
        }
    }
}