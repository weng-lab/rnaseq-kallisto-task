package step

import util.*
import java.nio.file.*
import util.CmdRunner

data class KallistoParameters (
    val r1: Path,
    val r2: Path? = null,
    val index: Path,
    val cores: Int = 1,
    val strandedness: String = "unstranded",
    val fragmentLength: Int? = null,
    val sdFragmentLength: Float? = null,
    val outputDirectory: Path,
    val outputPrefix: String = "output"
)

val STRANDED: Map<String, String> = mapOf(
    "unstranded" to "",
    "forward" to "--fr-stranded",
    "reverse" to "--rf-stranded"
)

fun CmdRunner.kallisto(parameters: KallistoParameters) {

    Files.createDirectories(parameters.outputDirectory)

    this.run("""
        kallisto quant --plaintext \
            -i ${parameters.index} \
            -t ${parameters.cores} \
            -o ${parameters.outputDirectory} \
            ${ STRANDED[parameters.strandedness] } \
            ${ if (parameters.r2 === null) "--single -l ${parameters.fragmentLength} -s ${parameters.sdFragmentLength}" else "" } \
            ${parameters.r1} ${ if (parameters.r2 !== null) parameters.r2 else "" }
    """)

    Files.move(
        parameters.outputDirectory.resolve("abundance.tsv"),
        parameters.outputDirectory.resolve("${parameters.outputPrefix}.abundance.tsv")
    )

}
