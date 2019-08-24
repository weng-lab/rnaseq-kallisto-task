package util

import mu.KotlinLogging
import java.io.File
import java.io.IOException
import java.lang.*

private val log = KotlinLogging.logger {}

interface CmdRunner {
    fun run(cmd: String)
    fun runCommand(cmd: String):String?
}

class DefaultCmdRunner : CmdRunner {
     override fun run(cmd: String) = exec("sh","-c",cmd)
     override fun runCommand(cmd: String):String? = getCommandOutput("sh","-c",cmd)
}

fun exec(vararg cmds: String) {
    log.info { "Executing command: ${cmds.toList()}" }
   val exitCode = ProcessBuilder(*cmds)
               .inheritIO()
                .start()
                .waitFor()
        if (exitCode != 0) {
            throw Exception("command failed with exit code $exitCode")
        }
}
fun getCommandOutput(vararg cmds: String):String? {
    log.info { "Executing command: ${cmds.toList()}" }
    val workingDir: File = File(".")
   return try {
       ProcessBuilder(*cmds)
               .directory(workingDir)
               .redirectOutput(ProcessBuilder.Redirect.PIPE)
               .redirectError(ProcessBuilder.Redirect.PIPE)
               .start().apply {
                   waitFor()
               }.inputStream.bufferedReader().readText()
   } catch (e:IOException){
       e.printStackTrace()
       null
   }

}