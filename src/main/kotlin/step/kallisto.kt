package step
import mu.KotlinLogging
import util.*
import java.nio.file.*
import util.CmdRunner
private val log = KotlinLogging.logger {}

fun CmdRunner.kallisto(repFile1:Path,repFile2:Path?,indexFile:Path,pairedEnd:Boolean,number_of_threads:Int,strandedness:String,fragment_length:Int?,sd_fragment_length:Float?, outDir:Path,outputPrefix:String) {
    log.info { "Make output Directory" }
    Files.createDirectories(outDir)
   this.run("tar xvf $indexFile -C $outDir")
    val rStrand = if(strandedness=="Unstranded") "" else if(strandedness=="forward")  "--fr-stranded" else "--rf-stranded"

    var cmd=""
    if(pairedEnd)
    {
        cmd="kallisto quant -i ${indexFile} \\\n" +
                "    -o ${outDir} \\\n" +
                "    -t ${number_of_threads} \\\n" +
                "    --plaintext \\\n" +
                "    ${rStrand} \\\n" +
                "    ${repFile1} ${repFile2}"
    }else {
        cmd="kallisto quant -i ${indexFile} \\\n" +
                "    -o ${outDir} \\\n" +
                "    -t ${number_of_threads} \\\n" +
                "    --plaintext \\\n" +
                "     ${rStrand} \\\n" +
                "    --single \\\n" +
                "    -l ${fragment_length} \\\n" +
                "    -s ${sd_fragment_length} \\\n" +
                "    ${repFile1}"
    }
    this.run(cmd)

    //rename
   val f1 = outDir.resolve("abundance.tsv")
    val f2 = outDir.resolve("${outputPrefix}_abundance.tsv")
    this.run("mv ${f1} ${f2}")

}
