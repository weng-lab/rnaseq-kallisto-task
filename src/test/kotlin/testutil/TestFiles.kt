package testutil
import java.nio.file.*

fun getResourcePath(relativePath: String): Path {

    val url = TestCmdRunner::class.java.classLoader.getResource(relativePath)
     return Paths.get(url.toURI())
}

// Resource Directories
val testInputResourcesDir = getResourcePath("test-input-files")


// Test Working Directories
val testDir = Paths.get("/tmp/rnaseq-test")!!
val testInputDir = testDir.resolve("input")!!
val testOutputDir = testDir.resolve("output")!!


val CTL_INDEX = testInputDir.resolve("ENCFF742NER.tar.gz")
val F1= testInputDir.resolve("ENCFF000VOR.fastq.gz")
