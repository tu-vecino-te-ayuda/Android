@file:Suppress("unused")

package org.tuvecinoteayuda.data.regions.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.tuvecinoteayuda.core.ext.letOrElse
import org.tuvecinoteayuda.core.util.getJsonDataFromAsset
import org.tuvecinoteayuda.data.DataContextWrapper
import org.tuvecinoteayuda.data.regions.models.City
import org.tuvecinoteayuda.data.regions.models.Region

class RegionRepository(private val contextWrapper: DataContextWrapper) {

    private var regions: List<Region>? = null

    fun getRegions(): List<Region> {
        return regions.letOrElse({ it }, {
            val fileData: String? =
                getJsonDataFromAsset(
                    contextWrapper.context,
                    REGIONS
                )
            val regionsListType = object : TypeToken<List<Region>>() {}.type
            return (fileData?.let { Gson().fromJson<List<Region>>(it, regionsListType) }
                ?: emptyList()).apply { regions = this }
        })
    }

    fun getRegionById(id: String): Region? {
        return getRegions().firstOrNull { it.id == id }
    }

    fun getRegionByName(name: String): Region? {
        return getRegions().firstOrNull { it.name == name }
    }

    fun getCitiesByRegionId(regionId: String): List<City> {
        return getRegionById(regionId)?.cities ?: listOf()
    }

    fun getCitiesByRegionName(regionName: String): List<City> {
        return getRegionByName(regionName)?.cities ?: listOf()
    }

    fun getCityById(regionId: String, cityId: String): City? {
        return getRegionById(regionId)?.cities?.firstOrNull { it.id == cityId }
    }

    fun getCityByName(regionName: String, cityName: String): City? {
        return getRegionByName(regionName)?.cities?.firstOrNull { it.name == cityName }
    }

    companion object {
        private const val REGIONS: String = "regions.json"

        fun newInstance(): RegionRepository {
            return RegionRepository(DataContextWrapper)
        }
    }
}
