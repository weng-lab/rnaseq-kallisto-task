package main

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.*
import step.*
import util.*
import java.nio.file.*
import util.CmdRunner

class Quant : CliktCommand() {
    
    private val r1: Path by option("--r1", help = "path to single-end or pair 1 reads")
        .path(exists = true).required()
    private val r2: Path? by option("--r2", help = "path to pair 2 reads (paired-end only)")
        .path()
    private val index: Path by option("--index", help = "path to kallisto index")
        .path(exists = true).required()
    private val strandedness: String by option("--strandedness", help = "strands to which reads belong; default is unstranded")
        .choice("forward", "reverse", "unstranded").default("unstranded")
    private val fragmentLength: Int? by option("--fragment-length",help = "fragment length for single end reads").int()
    private val sdFragmentLength: Float? by option(
        "--sd-fragment-length", help = "standard deviation of fragment length for single end reads"
    ).float()
    private val outputPrefix: String by option("--output-prefix", help = "output file name prefix; defaults to 'output'").default("output")

    private val cores: Int by option("--cores", help = "number of cores available to the task; default 1").int().default(1)
    private val outputDirectory by option("--output-directory", help = "path to output directory")
        .path().required()

    override fun run() {
        val cmdRunner = DefaultCmdRunner()
        cmdRunner.kallistoQuant(
            KallistoQuantParameters(
                r1 = r1,
                r2 = r2,
                index = index,
                strandedness = strandedness,
                fragmentLength = fragmentLength,
                sdFragmentLength = sdFragmentLength,
                outputDirectory = outputDirectory,
                outputPrefix = outputPrefix,
                cores = cores
            )
        )
    }

}
