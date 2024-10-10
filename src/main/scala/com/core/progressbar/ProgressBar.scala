package com.core.progressbar

import java.lang.ref.Cleaner
import java.util.concurrent.locks.ReentrantLock

/** A simple progress bar that can be used to show the progress of a task.
  *
  * The progress bar will be immediately displayed when created. Call `update` or `increment` to increase the progress
  * by a certain amount.
  *
  * The bar is rendered by a background thread, so be sure to call `finish` to ensure that the progress bar is fully
  * displayed and the thread is joined.
  *
  * ## Example
  * ```
  * val progressBar = new ProgressBar(100)
  * for (i <- 1 to 100) {
  *    progressBar.increment()
  *    Thread.sleep(100)
  * }
  * progressBar.finish()
  * ```
  */
class ProgressBar {
    private val width = 32
    private val lock = new ReentrantLock()
    private val spinnerChars = Array("|", "/", "-", "\\")
    private val spaceChar = "░"
    private val blockChar = "█"
    private var lastPrinted = 0
    private var total = 0
    private var progress = 0
    private var spinner = 0
    private var name = "Progress"
    private var displayData: Any = null

    @volatile private var isCancelled = false
    private var thread = Thread.currentThread()

    private val cleaner = Cleaner.create()
    private val cleanable = cleaner.register(this, () => { isCancelled = true; thread.join() })

    private val renderTask = new Runnable {
        def run() = {
            while (!isCancelled && progress < total) {
                if (progress != lastPrinted) {
                    updateConsole()
                }
            }
            updateConsole()
        }
    }

    private def updateConsole() = {
        val percent = math.min(progress.toDouble / total * 100, 100).toInt
        val blocks = (percent * width / 100).toInt
        val spaces = width - blocks
        val spinnerChar = spinnerChars(spinner)
        spinner = (spinner + 1) % spinnerChars.length

        val sb = new StringBuilder()        
        sb.append(s"$spinnerChar")
        sb.append(" ")
        sb.append(s"[$name]")
        sb.append(" ")
        sb.append(Console.BLUE)
        sb.append(blockChar * blocks)
        sb.append(spaceChar * spaces)
        sb.append(s"${Console.RESET} ${percent}%")

        if (displayData != null) {
            sb.append(s" ${displayData}")
        }
        print(s"\r${sb}")
        lastPrinted = progress

        if (progress >= total) {
            isCancelled = true
            println()
        }
    }

    def this(total: Int) = {
        this()
        this.total = total
        thread = new Thread(renderTask)
        thread.start()
    }

    def this(total: Int, name: String) = {
        this(total)
        this.name = name
    }

    /** Cancel the progress bar and join the thread. Will not update the progress bar.
      */
    def cancel() = {
        isCancelled = true
        thread.join()
    }

    /** Finish the progress bar and join the thread. Will update the progress bar to 100%.
      */
    def finish() = {
        progress = total
        cancel()
    }

    /** Update the progress by a certain amount.
      *
      * @param amount
      */
    def update(amount: Int = 1) = {
        try {
            lock.lock()
            progress += amount
        } finally {
            lock.unlock()
        }
    }

    /** Increment the progress by 1.
      */
    def increment() = update(1)

    /** Set the display data for the progress bar.
      *
      * Not thread safe because it is only used for display purposes.
      *
      * @param data
      */
    def setDisplayData(data: Any) = {
        displayData = data
    }
}
