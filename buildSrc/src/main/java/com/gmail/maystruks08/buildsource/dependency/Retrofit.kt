@file:Suppress("unused")

object Retrofit {
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val serializationConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.serializationConverter}"
}

//@Composable
//fun CustomKeyboard(
//    onClick: (digit: String) -> Unit
//) {
//    val inputVal = remember { mutableStateOf("") }
//    Row {
//        NumberKeys(onClick, Modifier.weight(1f))
//        Column(modifier = Modifier.weight(1f)) {
//            NumberButton(number = "<", onClick = onClick, modifier = Modifier.weight(1f))
//            NumberButton(number = "00", onClick = onClick, modifier = Modifier.weight(1f))
//            NumberButton(number = "ok", onClick = onClick, modifier = Modifier.weight(2f))
//        }
//    }
//
//}