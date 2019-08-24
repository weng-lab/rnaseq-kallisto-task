package util


fun strip_ext_bam(bam:String):String{
    val regex = """.(bam|Bam)""".toRegex()
    return regex.replace(bam, "")
}
fun strip_ext(f:String,ext:String):String{
    val regex = """.(${ext}|${ext})""".toRegex();
    return  regex.replace(f, "")
}
fun strip_ext_ta(ta:String):String{
    val regex = """\.[0-9]*(tagAlign|TagAlign|ta|Ta)\.gz$""".toRegex()
    return regex.replace(ta, "")
}

fun human_readable_number(num:Int):String {
    var number = num
    val units:Array<String> = arrayOf("","K","M","G","T","P")
    for(d in units){
        if(Math.abs(number) < 1000)
        {
            return "${number}${d}"
        }
        number = number /1000
    }
    return "${number}E"
}