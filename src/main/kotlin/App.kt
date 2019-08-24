import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.*
import step.*
import util.*
import java.nio.file.*
import util.CmdRunner


fun main(args: Array<String>) = Cli().main(args)

class Cli : CliktCommand() {
    private val repFile1: Path by option("-repFile1", help = "path to fastq rep file")
            .path(exists = true).required()
    private val repFile2: Path? by option("-repFile2", help = "path to fastq rep2 file")
            .path()

    private val indexFile:Path by option("-indexFile", help = "path to index tar file")
            .path(exists = true).required()

    private val pairedEnd: Boolean by option("-pairedEnd", help = "Paired-end BAM.").flag()
    private val number_of_threads: Int by option("-numberofthreads", help = "Number of cpus available.").int().required()
    private val strandedness:String by option("-strandedness",help = "read strand").choice("forward","reverse","Unstranded").required()

    private val fragment_length:Int? by option("-fragment_length",help = "fragment_length for single end").int()
    private val sd_fragment_length:Float? by option("-sd_fragment_length",help = "sd of frag length for single end").float()
    private val outputPrefix: String by option("-outputPrefix", help = "output file name prefix; defaults to 'output'").default("output")
    private val outDir by option("-outputDir", help = "path to output Directory")
        .path().required()


    override fun run() {
        val cmdRunner = DefaultCmdRunner()
        cmdRunner.runTask(repFile1,repFile2,indexFile, pairedEnd,number_of_threads,strandedness,fragment_length,sd_fragment_length,outDir,outputPrefix)
    }
}

/**
 * Runs pre-processing and bwa for raw input files
 *
 * @param taFiles pooledTa Input
 * @param outDir Output Path
 */
fun CmdRunner.runTask(repFile1:Path,repFile2:Path?,indexFile:Path,pairedEnd:Boolean,number_of_threads:Int,strandedness:String,fragment_length:Int?,sd_fragment_length:Float?, outDir:Path,outputPrefix:String) {

    kallisto(repFile1,repFile2,indexFile, pairedEnd,number_of_threads,strandedness,fragment_length,sd_fragment_length,outDir,outputPrefix)
}