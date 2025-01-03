package com.abcd.vian_marketplaceweddingorganizer.utils

import com.abcd.vian_marketplaceweddingorganizer.data.model.KabKotaModel
import com.abcd.vian_marketplaceweddingorganizer.data.model.KecamatanModel

class KotaKabProvIndonesia {
    fun provinsi(){
        val itemsProv =
            arrayOf(
                "Aceh",
                "Bali",
                "Banten",
                "Bengkulu",
                "D.I Yogyakarta",
                "D.K.I Jakarta",
                "Gorontalo",
                "Jambi",
                "Jawa Barat",
                "Jawa Tengah",
                "Jawa Timur",
                "Kalimantan Barat",
                "Kalimantan Selatan",
                "Kalimantan Tengah",
                "Kalimantan Timur",
                "Kalimantan Utara",
                "Kepulauan Bangka Belitung",
                "Kepulauan Riau",
                "Lampung",
                "Maluku",
                "Maluku Utara",
                "Nusa Tenggara Barat",
                "Nusa Tenggara Timur",
                "Papua",
                "Papua Barat",
                "Riau",
                "Sulawesi Barat",
                "Sulawesi Selatan",
                "Sulawesi Tengah",
                "Sulawesi Tenggara",
                "Sulawesi Utara",
                "Sumatera Barat",
                "Sumatera Selatan",
                "Sumatera Utara",
            )
    }

    fun fetchKotaKab(provinsi:String): Array<String> {
        var itemsKab: Array<String>

        //Ubah selectedItem jadi provinsi yang terpilih
        when (provinsi) {
            "Aceh" -> itemsKab = arrayOf(
                "Kabupaten Aceh Barat",
                "Kabupaten Aceh Barat Daya",
                "Kabupaten Aceh Besar",
                "Kabupaten Aceh Jaya",
                "Kabupaten Aceh Selatan",
                "Kabupaten Aceh Singkil",
                "Kabupaten Aceh Tamiang",
                "Kabupaten Aceh Tengah",
                "Kabupaten Aceh Tenggara",
                "Kabupaten Aceh Timur",
                "Kabupaten Aceh Utara",
                "Kabupaten Bener Meriah",
                "Kabupaten Bireuen",
                "Kabupaten Gayo Lues",
                "Kabupaten Nagan Raya",
                "Kabupaten Pidie",
                "Kabupaten Pidie Jaya",
                "Kabupaten Simeulue",
                "Kota Banda Aceh",
                "Kota Langsa",
                "Kota Lhokseumawe",
                "Kota Sabang",
                "Kota Subulussalam"
            )

            "Bali" -> itemsKab = arrayOf(
                "Kabupaten Badung",
                "Kabupaten Bangil",
                "Kabupaten Buleleng",
                "Kabupaten Gianyar",
                "Kabupaten Jembrana",
                "Kabupaten Karangasem",
                "Kabupaten Klungkung",
                "Kabupaten Tabanan",
                "Kota Denpasar"
            )

            "Banten" -> itemsKab = arrayOf(
                "Kabupaten Lebak",
                "Kabupaten Pandeglang",
                "Kabupaten Serang",
                "Kabupaten Tangerang",
                "Kota Cilegon",
                "Kota Serang",
                "Kota Tangerang",
                "Kota Tangerang selatan"
            )

            "Bengkulu" -> itemsKab = arrayOf(
                "Kabupaten Bengkulu Selatan",
                "Kabupaten Bemgkulu Tengah",
                "Kabupaten Bengkulu Utara",
                "Kabupaten Kaur",
                "Kabupaten kapahiang",
                "Kabupaten Lebong",
                "Kabupaten Mukomuko",
                "Kabupaten Rejang Lebong",
                "Kabupaten seluma",
                "Kota Bengkulu"
            )

            "D.I Yogyakarta" -> itemsKab = arrayOf(
                "Kabupaten Bantul",
                "Kabupaten Gunung kildul",
                "Kabupaten Kulon Progo",
                "Kabupaten Sleman",
                "Kota Yogyakarta"
            )

            "D.K.I Jakarta" -> itemsKab = arrayOf(
                "Kabupaten Kepulauan Seribu",
                "Kota Jakarta Barat",
                "Kota Jakarta Pusat",
                "Kota Jakarta Selatan",
                "Kota Jakarta Timur",
                "Kota Jakarta Utara"
            )

            "Gorontalo" -> itemsKab = arrayOf(
                "Kabupaten Boalemo",
                "Kabupaten Bone Bolango",
                "Kabupaten Gorontalo",
                "Kabupaten gorontalo Utara",
                "Kabupaten Pahuwato",
                "Kota Gorontalo"
            )

            "Jambi" -> itemsKab = arrayOf(
                "Kabupaten Batanghari",
                "Kabupaten Bungo",
                "Kabupaten Kerinci",
                "Kabupaten Merangin",
                "Kabupaten Muaro Jambi",
                "Kabupaten Sarolangun",
                "Kabupaten Tanjung Jabung Barat",
                "Kabupaten Tanjung Jabung Timur",
                "Kabupaten Tebo",
                "Kota Jambi",
                "Kota Sungai Penuh"
            )

            "Jawa Barat" -> itemsKab = arrayOf(
                "Kabupaten Bandung",
                "Kabupaten Bandung Barat",
                "Kabupaten Bekasi",
                "Kabupaten Bogor",
                "Kabupaten Ciamis",
                "Kabupaten Cianjur",
                "Kabupaten Cirebon",
                "Kabupaten Garut",
                "Kabupaten Indramayu",
                "Kabupaten Karawang",
                "Kabupaten Kuningan",
                "Kabupaten Majalengka",
                "Kabupaten Pangandaran",
                "Kabupaten Purwakarta",
                "Kabupaten Subang",
                "Kabupaten Sukabumi",
                "Kabupaten Sumedang",
                "Kabupaten Tasikmalaya",
                "Kota Bandung",
                "Kota Banjar",
                "Kota Bekasi",
                "Kota Bogor",
                "Kota Cimahi",
                "Kota Cirebon",
                "Kota Depok",
                "Kota Sukabumi",
                "Kota Tasikmalaya"
            )

            "Jawa Tengah" -> itemsKab = arrayOf(
                "Kabupaten Banjarnegara",
                "Kabupaten Banyumas",
                "Kabupaten Batang",
                "Kabupaten Blora",
                "Kabupaten Boyolali",
                "Kabupaten Brebes",
                "Kabupaten Cilacap",
                "Kabupaten Demak",
                "Kabupaten Grobogan",
                "Kabupaten Jepara",
                "Kabupaten Karanganyar",
                "Kabupaten Kebumen",
                "Kabupaten Kendal",
                "Kabupaten Klaten",
                "Kabupaten Kudus",
                "Kabupaten Magelang",
                "Kabupaten Pati",
                "Kabupaten Pekalongan",
                "Kabupaten Pemalang",
                "Kabupaten Purbalingga",
                "Kabupaten Purworejo",
                "Kabupaten Rembang",
                "Kabupaten Semarang",
                "Kabupaten Sragen",
                "Kabupaten Sukoharjo",
                "Kabupaten Tegal",
                "Kabupaten Temanggung",
                "Kabupaten Wonogiri",
                "Kabupaten Wonosobo",
                "Kota Magelang",
                "Kota Pekalongan",
                "Kota Salatiga",
                "Kota Semarang",
                "Kota Surakarta",
                "Kota Tegal"
            )

            "Jawa Timur" -> itemsKab = arrayOf(
                "Kabupaten Bangkalan",
                "Kabupaten Banyuwangi",
                "Kabupaten Blitar",
                "Kabupaten Bojonegoro",
                "Kabupaten Bondowoso",
                "Kabupaten Gresik",
                "Kabupaten Jember",
                "Kabupaten Jombang",
                "Kabupaten Kediri",
                "Kabupaten Lamongan",
                "Kabupaten Lumajang",
                "Kabupaten Madiun",
                "Kabupaten Magetan",
                "Kabupaten Malang",
                "Kabupaten Mojokerto",
                "Kabupaten Nganjuk",
                "Kabupaten Ngawi",
                "Kabupaten Pacitan",
                "Kabupaten Pamekasan",
                "Kabupaten Pasuruan",
                "Kabupaten Ponorogo",
                "Kabupaten Probolinggo",
                "Kabupaten Sampang",
                "Kabupaten Sidoarjo",
                "Kabupaten Situbondo",
                "Kabupaten Sumenep",
                "Kabupaten Trenggalek",
                "Kabupaten Tuban",
                "Kabupaten Tulungagung",
                "Kota Batu",
                "Kota Blitar",
                "Kota Kediri",
                "Kota Madiun",
                "Kota Malang",
                "Kota Mojokerto",
                "Kota Pasuruan",
                "Kota Probolinggo",
                "Kota Surabaya"
            )

            "Kalimantan Barat" -> itemsKab = arrayOf(
                "Kabupaten Bengkayang",
                "Kabupaten Kapuas Hulu",
                "Kabupaten Kayong Utara",
                "Kabupaten Ketapang",
                "Kabupaten Kubu Raya",
                "Kabupaten Landak",
                "Kabupaten Melawi",
                "Kabupaten Pontianak",
                "Kabupaten Sambas",
                "Kabupaten Sanggau",
                "Kabupaten Sekadau",
                "Kabupaten Sintang",
                "Kota Pontianak",
                "Kota Singkawang"
            )

            "Kalimantan Selatan" -> itemsKab = arrayOf(
                "Kabupaten Balangan",
                "Kabupaten Banjar",
                "Kabupaten Barito Kuala",
                "Kabupaten Hulu Sungai Selatan",
                "Kabupaten Hulu Sungai Tengah",
                "Kabupaten Hulu Sungai Utara",
                "Kabupaten Kotabaru",
                "Kabupaten Tabalong",
                "Kabupaten Tanah Bumbu",
                "Kabupaten Tanah Laut",
                "Kabupaten Tapin",
                "Kota Banjarbaru",
                "Kota Banjarmasin"
            )

            "Kalimantan Tengah" -> itemsKab = arrayOf(
                "Kabupaten Barito Selatan",
                "Kabupaten Barito Timur",
                "Kabupaten Barito Utara",
                "Kabupaten Gunung Mas",
                "Kabupaten Kapuas",
                "Kabupaten Katingan",
                "Kabupaten Kotawaringin Barat",
                "Kabupaten Kotawaringin Timur",
                "Kabupaten Lamandau",
                "Kabupaten Murung Raya",
                "Kabupaten Pulang Pisau",
                "Kabupaten Sukamara",
                "Kabupaten Seruyan",
                "Kota Palangka Raya"
            )

            "Kalimantan Timur" -> itemsKab = arrayOf(
                "Kabupaten Berau",
                "Kabupaten Kutai Barat",
                "Kabupaten Kutai Kartanegara",
                "Kabupaten Kutai Timur",
                "Kabupaten Paser",
                "Kabupaten Penajam Paser Utara",
                "Kabupaten Mahakam Ulu",
                "Kota Balikpapan",
                "Kota Bontang",
                "Kota Samarinda"
            )

            "Kalimantan Utara" -> itemsKab = arrayOf(
                "Kabupaten Bulungan",
                "Kabupaten Malinau",
                "Kabupaten Nunukan",
                "Kabupaten Tana Tidung",
                "Kota Tarakan"
            )

            "Kepulauan Bangka Belitung" -> itemsKab = arrayOf(
                "Kabupaten Bangka",
                "Kabupaten Bangka Barat",
                "Kabupaten Bangka Selatan",
                "Kabupaten Bangka Tengah",
                "Kabupaten Belitung",
                "Kabupaten Belitung Timur",
                "Kota Pangkal Pinang"
            )

            "Kepulauan Riau" -> itemsKab = arrayOf(
                "Kabupaten Bintan",
                "Kabupaten Karimun",
                "Kabupaten Kepulauan Anambas",
                "Kabupaten Lingga",
                "Kabupaten Natuna",
                "Kota Batam",
                "Kota Tanjung Pinang"
            )

            "Lampung" -> itemsKab = arrayOf(
                "Kabupaten Lampung Barat",
                "Kabupaten Lampung Selatan",
                "Kabupaten Lampung Tengah",
                "Kabupaten Lampung Timur",
                "Kabupaten Lampung Utara",
                "Kabupaten Mesuji",
                "Kabupaten Pesawaran",
                "Kabupaten Pringsewu",
                "Kabupaten Tanggamus",
                "Kabupaten Tulang Bawang",
                "Kabupaten Tulang Bawang Barat",
                "Kabupaten Way Kanan",
                "Kabupaten Pesisir Barat",
                "Kota Bandar Lampung",
                "Kota Kotabumi",
                "Kota Liwa",
                "Kota Metro"
            )

            "Maluku" -> itemsKab = arrayOf(
                "Kabupaten Buru",
                "Kabupaten Buru Selatan",
                "Kabupaten Kepulauan Aru",
                "Kabupaten Maluku Barat Daya",
                "Kabupaten Maluku Tengah",
                "Kabupaten Maluku Tenggara",
                "Kabupaten Maluku Tenggara Barat",
                "Kabupaten Seram Bagian Barat",
                "Kabupaten Seram Bagian Timur",
                "Kota Ambon",
                "Kota Tual"
            )

            "Maluku Utara" -> itemsKab = arrayOf(
                "Kabupaten Halmahera Barat",
                "Kabupaten Halmahera Tengah",
                "Kabupaten Halmahera Utara",
                "Kabupaten Halmahera Selatan",
                "Kabupaten Halmahera Timur",
                "Kabupaten Kepulauan Sula",
                "Kabupaten Pulau Morotai",
                "Kabupaten Pulau Taliabu",
                "Kota Ternate",
                "Kota Tidore Kepulauan"
            )

            "Nusa Tenggara Barat" -> itemsKab = arrayOf(
                "Kabupaten Bima",
                "Kabupaten Dompu",
                "Kabupaten Lombok Barat",
                "Kabupaten Lombok Tengah",
                "Kabupaten Lombok Timur",
                "Kabupaten Lombok Utara",
                "Kabupaten Sumbawa",
                "Kabupaten Sumbawa Barat",
                "Kota Bima",
                "Kota Mataram"
            )

            "Nusa Tenggara Timur" -> itemsKab = arrayOf(
                "Kabupaten Alor",
                "Kabupaten Belu",
                "Kabupaten Ende",
                "Kabupaten Flores Timur",
                "Kabupaten Kupang",
                "Kabupaten Lembata",
                "Kabupaten Manggarai",
                "Kabupaten Manggarai Barat",
                "Kabupaten Manggarai Timur",
                "Kabupaten Ngada",
                "Kabupaten Nagekeo",
                "Kabupaten Rote Ndao",
                "Kabupaten Sabu Raijua",
                "Kabupaten Sikka",
                "Kabupaten Sumba Barat",
                "Kabupaten Sumba Barat Daya",
                "Kabupaten Sumba Tengah",
                "Kabupaten Sumba Timur",
                "Kabupaten Timor Tengah Selatan",
                "Kabupaten Timor Tengah Utara",
                "Kota Kupang"
            )

            "Papua" -> itemsKab = arrayOf(
                "Kabupaten Asmat",
                "Kabupaten Biak Numfor",
                "Kabupaten Boven Digoel",
                "Kabupaten Deiyai",
                "Kabupaten Dogiyai",
                "Kabupaten Intan Jaya",
                "Kabupaten Jayapura",
                "Kabupaten Jayawijaya",
                "Kabupaten Keerom",
                "Kabupaten Kepulauan Yapen",
                "Kabupaten Lanny Jaya",
                "Kabupaten Mamberamo Raya",
                "Kabupaten Mamberamo Tengah",
                "Kabupaten Mappi",
                "Kabupaten Merauke",
                "Kabupaten Mimika",
                "Kabupaten Nabire",
                "Kabupaten Nduga",
                "Kabupaten Paniai",
                "Kabupaten Pegunungan Bintang",
                "Kabupaten Puncak",
                "Kabupaten Puncak Jaya",
                "Kabupaten Sarmi",
                "Kabupaten Supiori",
                "Kabupaten Tolikara",
                "Kabupaten Waropen",
                "Kabupaten Yahukimo",
                "Kabupaten Yalimo",
                "Kota Jayapura"
            )

            "Papua Barat" -> itemsKab = arrayOf(
                "Kabupaten Fakfak",
                "Kabupaten Kaimana",
                "Kabupaten Manokwari",
                "Kabupaten Manokwari Selatan",
                "Kabupaten Maybrat",
                "Kabupaten Pegunungan Arfak",
                "Kabupaten Raja Ampat",
                "Kabupaten Sorong",
                "Kabupaten Sorong Selatan",
                "Kabupaten Tambrauw",
                "Kabupaten Teluk Bintuni",
                "Kabupaten Teluk Wondama",
                "Kota Sorong"
            )

            "Riau" -> itemsKab = arrayOf(
                "Kabupaten Bengkalis",
                "Kabupaten Indragiri Hilir",
                "Kabupaten Indragiri Hulu",
                "Kabupaten Kampar",
                "Kabupaten Kuantan Singingi",
                "Kabupaten Pelalawan",
                "Kabupaten Rokan Hilir",
                "Kabupaten Rokan Hulu",
                "Kabupaten Siak",
                "Kabupaten Kepulauan Meranti",
                "Kota Dumai",
                "Kota Pekanbaru"
            )

            "Sulawesi Barat" -> itemsKab = arrayOf(
                "Kabupaten Majene",
                "Kabupaten Mamasa",
                "Kabupaten Mamuju",
                "Kabupaten Mamuju Utara",
                "Kabupaten Polewali Mandar",
                "Kabupaten Mamuju Tengah"
            )

            "Sulawesi Selatan" -> itemsKab = arrayOf(
                "Kabupaten Bantaeng",
                "Kabupaten Barru",
                "Kabupaten Bone	Watampone",
                "Kabupaten Bulukumba",
                "Kabupaten Enrekang",
                "Kabupaten Gowa",
                "Kabupaten Jeneponto",
                "Kabupaten Kepulauan Selayar",
                "Kabupaten Luwu",
                "Kabupaten Luwu Timur",
                "Kabupaten Luwu Utara",
                "Kabupaten Maros",
                "Kabupaten Pangkajene dan Kepulauan",
                "Kabupaten Pinrang",
                "Kabupaten Sidenreng Rappang",
                "Kabupaten Sinjai",
                "Kabupaten Soppeng",
                "Kabupaten Takalar",
                "Kabupaten Tana Toraja",
                "Kabupaten Toraja Utara",
                "Kabupaten Wajo",
                "Kota Makassar",
                "Kota Palopo",
                "Kota Parepare"
            )

            "Sulawesi Tengah" -> itemsKab = arrayOf(
                "Kabupaten Banggai",
                "Kabupaten Banggai Kepulauan",
                "Kabupaten Banggai Laut",
                "Kabupaten Buol",
                "Kabupaten Donggala",
                "Kabupaten Morowali",
                "Kabupaten Parigi Moutong",
                "Kabupaten Poso",
                "Kabupaten Sigi",
                "Kabupaten Tojo Una-Una",
                "Kabupaten Tolitoli",
                "Kota Palu"
            )

            "Sulawesi Tenggara" -> itemsKab = arrayOf(
                "Kabupaten Bombana",
                "Kabupaten Buton",
                "Kabupaten Buton Utara",
                "Kabupaten Kolaka",
                "Kabupaten Kolaka Timur",
                "Kabupaten Kolaka Utara",
                "Kabupaten Konawe",
                "Kabupaten Konawe Selatan",
                "Kabupaten Konawe Utara",
                "Kabupaten Konawe Kepulauan",
                "Kabupaten Muna",
                "Kabupaten Wakatobi",
                "Kota Bau-Bau",
                "Kota Kendari"
            )

            "Sulawesi Utara" -> itemsKab = arrayOf(
                "Kabupaten Bolaang Mongondow",
                "Kabupaten Bolaang Mongondow Selatan",
                "Kabupaten Bolaang Mongondow Timur",
                "Kabupaten Bolaang Mongondow Utara",
                "Kabupaten Kepulauan Sangihe",
                "Kabupaten Kepulauan Siau Tagulandang Biaro",
                "Kabupaten Kepulauan Talaud",
                "Kabupaten Minahasa",
                "Kabupaten Minahasa Selatan",
                "Kabupaten Minahasa Tenggara",
                "Kabupaten Minahasa Utara",
                "Kota Bitung",
                "Kota Kotamobagu",
                "Kota Manado",
                "Kota Tomohon"
            )

            "Sumatera Barat" -> itemsKab = arrayOf(
                "Kabupaten Agam",
                "Kabupaten Dharmasraya",
                "Kabupaten Kepulauan Mentawai",
                "Kabupaten Lima Puluh Kota",
                "Kabupaten Padang Pariaman",
                "Kabupaten Pasaman",
                "Kabupaten Pasaman Barat",
                "Kabupaten Pesisir Selatan",
                "Kabupaten Sijunjung",
                "Kabupaten Solok",
                "Kabupaten Solok Selatan",
                "Kabupaten Tanah Datar",
                "Kota Bukittinggi",
                "Kota Padang",
                "Kota Padangpanjang",
                "Kota Pariaman",
                "Kota Payakumbuh",
                "Kota Sawahlunto",
                "Kota Solok"
            )

            "Sumatera Selatan" -> itemsKab = arrayOf(
                "Kabupaten Banyuasin",
                "Kabupaten Empat Lawang",
                "Kabupaten Lahat",
                "Kabupaten Muara Enim",
                "Kabupaten Musi Banyuasin",
                "Kabupaten Musi Rawas",
                "Kabupaten Ogan Ilir",
                "Kabupaten Ogan Komering Ilir",
                "Kabupaten Ogan Komering Ulu",
                "Kabupaten Ogan Komering Ulu Selatan",
                "Kabupaten Penukal Abab Lematang Ilir",
                "Kabupaten Ogan Komering Ulu Timur",
                "Kota Lubuklinggau",
                "Kota Pagar Alam",
                "Kota Palembang",
                "Kota Prabumulih"
            )

            "Sumatera Utara" -> itemsKab = arrayOf(
                "Kabupaten Asahan",
                "Kabupaten Batubara",
                "Kabupaten Dairi",
                "Kabupaten Deli Serdang",
                "Kabupaten Humbang Hasundutan",
                "Kabupaten Karo	Kabanjahe",
                "Kabupaten Labuhanbatu",
                "Kabupaten Labuhanbatu Selatan",
                "Kabupaten Labuhanbatu Utara",
                "Kabupaten Langkat",
                "Kabupaten Mandailing Natal",
                "Kabupaten Nias",
                "Kabupaten Nias Barat",
                "Kabupaten Nias Selatan",
                "Kabupaten Nias Utara",
                "Kabupaten Padang Lawas",
                "Kabupaten Padang Lawas Utara",
                "Kabupaten Pakpak Bharat",
                "Kabupaten Samosir",
                "Kabupaten Serdang Bedagai",
                "Kabupaten Simalungun",
                "Kabupaten Tapanuli Selatan",
                "Kabupaten Tapanuli Tengah",
                "Kabupaten Tapanuli Utara",
                "Kabupaten Toba Samosir",
                "Kota Binjai",
                "Kota Gunungsitoli",
                "Kota Medan",
                "Kota Padangsidempuan",
                "Kota Pematangsiantar",
                "Kota Sibolga",
                "Kota Tanjungbalai",
                "Kota Tebing Tinggi"
            )

            else -> itemsKab = arrayOf("")
        }

        return itemsKab
    }

    fun kotaKabSulsel(): ArrayList<String> {

        return arrayListOf(
            "Kabupaten Bantaeng",
            "Kabupaten Barru",
            "Kabupaten Bone	Watampone",
            "Kabupaten Bulukumba",
            "Kabupaten Enrekang",
            "Kabupaten Gowa",
            "Kabupaten Jeneponto",
            "Kabupaten Kepulauan Selayar",
            "Kabupaten Luwu",
            "Kabupaten Luwu Timur",
            "Kabupaten Luwu Utara",
            "Kabupaten Maros",
            "Kabupaten Pangkajene dan Kepulauan",
            "Kabupaten Pinrang",
            "Kabupaten Sidenreng Rappang",
            "Kabupaten Sinjai",
            "Kabupaten Soppeng",
            "Kabupaten Takalar",
            "Kabupaten Tana Toraja",
            "Kabupaten Toraja Utara",
            "Kabupaten Wajo",
            "Kota Makassar",
            "Kota Palopo",
            "Kota Parepare"
        )
    }

    fun kecamatan(kotaKab: String){
        var itemsKecamatan: Array<String>

        //Ubah selectedItem jadi provinsi yang terpilih
        when (kotaKab) {
            "Kabupaten Bantaeng" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Barru" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Bone	Watampone" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Bulukumba" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Enrekang" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Gowa" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Jeneponto" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Kepulauan Selayar" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Luwu" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Luwu Timur" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Luwu Utara" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Maros" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Pangkajene dan Kepulauan" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Pinrang" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Sidenreng Rappang" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Sinjai" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Soppeng" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Takalar" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Tana Toraja" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Toraja Utara" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kabupaten Wajo" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kota Makassar" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kota Palopo" -> itemsKecamatan = arrayOf(
                "",
                ""
            )
            "Kota Parepare"-> itemsKecamatan = arrayOf(

            )
            else -> itemsKecamatan = arrayOf()
        }
    }

//    fun kabKotaValue(){
//        var bantaeng = KabKotaModel("Kabupaten Bantaeng",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var barru = KabKotaModel("Kabupaten Barru",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var bone = KabKotaModel("Kabupaten Bone	Watampone",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var bulukumba = KabKotaModel("Kabupaten Bulukumba",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var enrekang = KabKotaModel("Kabupaten Enrekang",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var gowa = KabKotaModel("Kabupaten Gowa",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var jeneponto = KabKotaModel("Kabupaten Jeneponto",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var selayar = KabKotaModel("Kabupaten Kepulauan Selayar",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var luwu = KabKotaModel("Kabupaten Luwu",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var luwuTimur = KabKotaModel("Kabupaten Luwu Timur",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var luwuUtara = KabKotaModel("Kabupaten Luwu Utara",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var maros = KabKotaModel("Kabupaten Maros",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var pangkep = KabKotaModel("Kabupaten Pangkajene dan Kepulauan",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var pinrang = KabKotaModel("Kabupaten Pinrang",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var sidrap = KabKotaModel("Kabupaten Sidenreng Rappang",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var sinjai = KabKotaModel("Kabupaten Sinjai",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var soppeng = KabKotaModel("Kabupaten Soppeng",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var takalar = KabKotaModel("Kabupaten Takalar",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var tanaToraja = KabKotaModel("Kabupaten Tana Toraja",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var torajaUtara = KabKotaModel("Kabupaten Toraja Utara",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var wajo = KabKotaModel("Kabupaten Wajo",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var makassar = KabKotaModel("Kota Makassar",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var palopo = KabKotaModel("Kota Palopo",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//        var parepare = KabKotaModel("Kota Parepare",
//            arrayListOf(
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                ),
//                KecamatanModel(
//                    "",
//                    arrayListOf(
//                        KecamatanKodePos(
//                            ""
//                        ),
//                        KecamatanKodePos(
//                            ""
//                        )
//                    )
//                )
//            )
//        )
//
//    }


}