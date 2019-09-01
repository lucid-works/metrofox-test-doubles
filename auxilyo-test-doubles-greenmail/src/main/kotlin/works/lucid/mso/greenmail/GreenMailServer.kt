package works.lucid.mso.greenmail

import com.icegreen.greenmail.configuration.PropertiesBasedGreenMailConfigurationBuilder
import com.icegreen.greenmail.server.BuildInfo
import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.PropertiesBasedServerSetupBuilder
import org.slf4j.LoggerFactory
import java.util.*

class GreenMailServer {

    private val log = LoggerFactory.getLogger(GreenMailServer::class.java)

    fun doRun(properties: Properties) {
        val serverSetup = PropertiesBasedServerSetupBuilder().build(properties)

        if (serverSetup.isEmpty()) {
            printUsage()
        } else {
            var greenMail = GreenMail(serverSetup)
            log.info("Starting GreenMail standalone v{} using {}",
                    BuildInfo.INSTANCE.projectVersion, Arrays.toString(serverSetup))
            greenMail.withConfiguration(PropertiesBasedGreenMailConfigurationBuilder().build(properties))
                    .start()
        }
    }

    private fun printUsage() {
        println("Usage: java OPTIONS -jar greenmail.jar")
        println("\nOPTIONS:")
        val options = arrayOf(arrayOf("-Dgreenmail.setup.<protocol|all>",
                "Specifies mail server to start using default port and bind " + "address 127.0.0.1"),
                arrayOf("Note: Protocol can be one of smtp,smtps,imap,imaps,pop3 or pop3s"),
                arrayOf("-Dgreenmail.setup.test.<protocol|all>", "Specifies mail server to start using default port plus " + "offset 3000 and bind address 127.0.0.1"),
                arrayOf("-Dgreenmail.<protocol|all>.hostname=...", "Specifies bind address. Requires additional port parameter."),
                arrayOf("-Dgreenmail.<protocol|all>.port=...", "Specifies port. Requires additional hostname parameter."),
                arrayOf("-Dgreenmail.users=<logon:pwd@domain>[,...]", "Specifies mail users, eg foo:pwd@bar.com,foo2:pwd@bar2.com."),
                arrayOf("Note: domain must be DNS resolvable!"),
                arrayOf("-Dgreenmail.auth.disabled ", "Disables authentication check so that any password works."),
                arrayOf("Also automatically provisions previously non-existent users."),
                arrayOf("-Dgreenmail.verbose ", "Enables verbose mode, including JavaMail debug output"),
                arrayOf("-Dgreenmail.startup.timeout=<TIMEOUT_IN_MILLISECS>", "Overrides the default server startup timeout of 1000ms."))
        for (opt in options) {
            if (opt.size == 1) {
                println(String.format("%1$44s %2\$s", " ", opt[0]))
            } else {
                println(String.format("%1$-42s : %2\$s", *opt as Array<Any>))
            }
        }
        println()
        println("""Example:
         java -Dgreenmail.setup.test.all -Dgreenmail.users=foo:pwd@bar.com -jar greenmail.jar
               Starts SMTP,SMTPS,IMAP,IMAPS,POP3,POP3S with default ports plus offset 3000 on 127.0.0.1 and user foo@bar.com.
              Note: bar.com domain for user must be DNS resolvable!
         java -Dgreenmail.setup.test.smtp -Dgreenmail.setup.test.imap -Dgreenmail.auth.disabled -jar greenmail.jar
               Starts SMTP on 127.0.01:3025 and IMAP on 127.0.0.1:3143, disabling user authentication
         java -Dgreenmail.smtp.hostname=0.0.0.0 -Dgreenmail.smtp.port=3025 -Dgreenmail.imap.hostname=0.0.0.0 -Dgreenmail.imap.port=3143 -jar greenmail.jar
              Starts SMTP on 0.0.0.0:3025 and IMAP on 0.0.0.0:3143""")
    }

}

fun main() {
    val properties = System.getProperties()
    GreenMailServer().doRun(properties)
}