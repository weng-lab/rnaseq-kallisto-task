import org.junit.jupiter.api.*
import step.*
import testutil.*
import testutil.cmdRunner
import testutil.setupTest
import java.nio.file.*
import org.assertj.core.api.Assertions.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KallistoTests {

    @BeforeAll fun setup() = setupTest()
    @AfterAll fun cleanup() = cleanupTest()

    @Test fun `test single-end quantifications`() {

        cmdRunner.kallistoQuant(
            KallistoQuantParameters(
                r1 = getResourcePath("test.fastq.gz"),
                index = getResourcePath("hg38.chrM.kallisto.idx"),
                outputDirectory = testDir,
                fragmentLength = 100,
                sdFragmentLength = 10.0F
            )
        )

        assertThat(testDir.resolve("output.abundance.tsv")).exists()
        testDir.resolve("output.abundance.tsv").toFile().bufferedReader().use {
            assertThat(it.readText().toMD5()).isEqualTo("5f9faa00904649814f7e030790164e6a")
        }

    }

    @Test fun `test index creation`() {

        cmdRunner.kallistoIndex(
            KallistoIndexParameters(
                sequences = listOf( getResourcePath("chrM.fa") ),
                output = testDir.resolve("test.index.output")
            )
        )

        assertThat(testDir.resolve("test.index.output")).exists()
        testDir.resolve("test.index.output").toFile().bufferedReader().use {
            assertThat(it.readText().toMD5()).isEqualTo("18d3584e21808a3d2a5ed1471adabed3")
        }

    }

}
