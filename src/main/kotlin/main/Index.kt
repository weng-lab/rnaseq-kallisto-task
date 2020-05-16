package main

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.*
import step.*
import util.*
import java.nio.file.*
import util.CmdRunner

class Index : CliktCommand() {
    
    private val sequences: List<Path> by option("--sequence", help = "path to genome sequences in FASTA format")
        .path(exists = true).multiple()
    private val output: Path by option("--output", help = "path where the index will be written")
        .path().required()
    private val kmerSize: Int by option("--kmer-size", help = "kmer size to use in the index; default 31")
        .int().default(31)
    private val makeUnique: Boolean by option("--make-unique", help = "if set, sequences with the same name will be made unique")
        .flag()

    override fun run() {
        val cmdRunner = DefaultCmdRunner()
        cmdRunner.kallistoIndex(
            KallistoIndexParameters(
                sequences = sequences,
                output = output,
                kmerSize = kmerSize,
                makeUnique = makeUnique
            )
        )
    }

}
