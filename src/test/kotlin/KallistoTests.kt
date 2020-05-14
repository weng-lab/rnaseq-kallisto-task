import org.junit.jupiter.api.*
import step.*
import testutil.*
import testutil.cmdRunner
import testutil.setupTest
import java.nio.file.*
import org.assertj.core.api.Assertions.*

class KallistoTests {

     @BeforeEach fun setup() = setupTest()
     @AfterEach fun cleanup() = cleanupTest()

     @Test fun `test single-end quantifications`() {

        cmdRunner.kallisto(
            KallistoParameters(
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

}
