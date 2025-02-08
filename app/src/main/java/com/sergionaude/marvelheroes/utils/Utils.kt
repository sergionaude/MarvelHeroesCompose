package com.sergionaude.marvelheroes.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import java.math.BigInteger
import java.security.MessageDigest

fun getHash(timeStamp: String, privateKey: String, publicKey: String) : String{

    val hashStr = timeStamp + privateKey + publicKey
    val md = MessageDigest.getInstance("MD5")

    return BigInteger(1, md.digest(hashStr.toByteArray()))
        .toString(16)
        .padStart(32, '0')
}

@Composable
fun CharacterImage(
    url: String,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.FillWidth
){
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
}