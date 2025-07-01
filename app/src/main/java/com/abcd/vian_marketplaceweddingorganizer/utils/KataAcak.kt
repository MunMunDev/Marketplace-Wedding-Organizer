package com.abcd.vian_marketplaceweddingorganizer.utils

class KataAcak {
    fun getHurufSaja(): String{
        val str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var hurufAcak = "1"
        for(i in 1..20){
            hurufAcak+=str.random()
        }
        return hurufAcak
    }
    fun getAngkaSaja(jumlahAngka: Int): String{
        val str = "1234567890"
        var hurufAcak = ""
        for(i in 1..jumlahAngka){
            hurufAcak += str.random()
        }
        return hurufAcak
    }
    fun getHurufDanAngka(): String{
        val str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
        var hurufAcak = "1"
        for(i in 1..20){
            hurufAcak+=str.random()
        }
        return hurufAcak
    }
}