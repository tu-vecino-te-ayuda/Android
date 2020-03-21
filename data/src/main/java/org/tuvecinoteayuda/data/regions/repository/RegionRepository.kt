package org.tuvecinoteayuda.data.regions.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.tuvecinoteayuda.data.regions.models.Region

class RegionRepository {

    fun getRegions(): List<Region> {
        val regionsListType = object : TypeToken<Array<Region>>() {}.type
        return Gson().fromJson(REGIONS, regionsListType)
    }

    companion object {
        private const val REGIONS: String =
            "[\n" +
                    "  {\n" +
                    "    \"id\": \"01\",\n" +
                    "    \"nm\": \"Araba/Álava\",\n" +
                    "    \"cities\": [\n" +
                    "      {\n" +
                    "        \"id\": \"01002\",\n" +
                    "        \"nm\": \"Amurrio\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01049\",\n" +
                    "        \"nm\": \"Añana\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01003\",\n" +
                    "        \"nm\": \"Aramaio\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01006\",\n" +
                    "        \"nm\": \"Armiñón\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01037\",\n" +
                    "        \"nm\": \"Arraia-Maeztu\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01008\",\n" +
                    "        \"nm\": \"Arratzua-Ubarrundia\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01004\",\n" +
                    "        \"nm\": \"Artziniega\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01009\",\n" +
                    "        \"nm\": \"Asparrena\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01010\",\n" +
                    "        \"nm\": \"Ayala/Aiara\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01011\",\n" +
                    "        \"nm\": \"Baños de Ebro/Mañueta\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01013\",\n" +
                    "        \"nm\": \"Barrundia\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01014\",\n" +
                    "        \"nm\": \"Berantevilla\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01016\",\n" +
                    "        \"nm\": \"Bernedo\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01017\",\n" +
                    "        \"nm\": \"Campezo/Kanpezu\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01021\",\n" +
                    "        \"nm\": \"Elburgo/Burgelu\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01022\",\n" +
                    "        \"nm\": \"Elciego\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01023\",\n" +
                    "        \"nm\": \"Elvillar/Bilar\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01046\",\n" +
                    "        \"nm\": \"Erriberagoitia/Ribera Alta\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01056\",\n" +
                    "        \"nm\": \"Harana/Valle de Arana\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01901\",\n" +
                    "        \"nm\": \"Iruña Oka/Iruña de Oca\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01027\",\n" +
                    "        \"nm\": \"Iruraiz-Gauna\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01019\",\n" +
                    "        \"nm\": \"Kripan\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01020\",\n" +
                    "        \"nm\": \"Kuartango\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01028\",\n" +
                    "        \"nm\": \"Labastida/Bastida\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01030\",\n" +
                    "        \"nm\": \"Lagrán\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01031\",\n" +
                    "        \"nm\": \"Laguardia\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01032\",\n" +
                    "        \"nm\": \"Lanciego/Lantziego\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01902\",\n" +
                    "        \"nm\": \"Lantarón\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01033\",\n" +
                    "        \"nm\": \"Lapuebla de Labarca\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01036\",\n" +
                    "        \"nm\": \"Laudio/Llodio\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01058\",\n" +
                    "        \"nm\": \"Legutio\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01034\",\n" +
                    "        \"nm\": \"Leza\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01039\",\n" +
                    "        \"nm\": \"Moreda de Álava/Moreda Araba\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01041\",\n" +
                    "        \"nm\": \"Navaridas\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01042\",\n" +
                    "        \"nm\": \"Okondo\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01043\",\n" +
                    "        \"nm\": \"Oyón-Oion\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01044\",\n" +
                    "        \"nm\": \"Peñacerrada-Urizaharra\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01047\",\n" +
                    "        \"nm\": \"Ribera Baja/Erribera Beitia\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01051\",\n" +
                    "        \"nm\": \"Salvatierra/Agurain\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01052\",\n" +
                    "        \"nm\": \"Samaniego\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01053\",\n" +
                    "        \"nm\": \"San Millán/Donemiliaga\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01054\",\n" +
                    "        \"nm\": \"Urkabustaiz\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01055\",\n" +
                    "        \"nm\": \"Valdegovía/Gaubea\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01057\",\n" +
                    "        \"nm\": \"Villabuena de Álava/Eskuernaga\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01059\",\n" +
                    "        \"nm\": \"Vitoria-Gasteiz\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01060\",\n" +
                    "        \"nm\": \"Yécora/Iekora\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01061\",\n" +
                    "        \"nm\": \"Zalduondo\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01062\",\n" +
                    "        \"nm\": \"Zambrana\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01018\",\n" +
                    "        \"nm\": \"Zigoitia\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": \"01063\",\n" +
                    "        \"nm\": \"Zuia\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  }\n" +
                    "]"

        fun newInstance(): RegionRepository {
            return RegionRepository()
        }
    }
}