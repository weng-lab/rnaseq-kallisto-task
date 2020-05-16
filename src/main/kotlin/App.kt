import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import main.Quant

class KallistoTask : CliktCommand() {
    override fun run() = Unit
}

fun main(args: Array<String>) = KallistoTask().subcommands(Quant()).main(args)
