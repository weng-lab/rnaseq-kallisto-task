package step

import util.*
import java.nio.file.*
import util.CmdRunner

data class KallistoIndexParameters (
    val sequences: List<Path>,
    val kmerSize: Int? = 31,
    val makeUnique: Boolean = false,
    val output: Path
)

fun CmdRunner.kallistoIndex(parameters: KallistoIndexParameters) {

    Files.createDirectories(parameters.output.parent)

    this.run("""
        kallisto index \
            --index ${parameters.output} \
            --kmer-size ${parameters.kmerSize} \
            ${ if (parameters.makeUnique) "--make-unique" else "" } \
            ${ parameters.sequences.joinToString(" ") }
    """)

}
