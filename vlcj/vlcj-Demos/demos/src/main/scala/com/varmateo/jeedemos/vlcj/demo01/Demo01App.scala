/**************************************************************************
 *
 * Copyright (c) 2019 Jorge Nunes
 *
 **************************************************************************/

package com.varmateo.jeedemos.vlcj.demo01

import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.Promise
import scala.util.Try

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent


object Demo01App {

    val APP_TITLE: String = "Demo01"

    val DEFAULT_WIDTH: Int = 640
    val DEFAULT_HEIGHT: Int = 480


    def apply(): Demo01App = new Demo01App()
}


final class Demo01App private () {

    import Demo01App._


    val mediaPlayerComponent: EmbeddedMediaPlayerComponent = new EmbeddedMediaPlayerComponent()


    def start(mrl: String): Unit = {

        setupGui()
            .flatMap(_ => playMedia(mrl))
            .onComplete(showPlayStatus(mrl, _))
    }


    private def playMedia(mrl: String): Future[Unit] = {

        val isSuccess: Boolean = mediaPlayerComponent.mediaPlayer().media().play(mrl)

        if (isSuccess)
            Future.unit
        else
            Future.failed(new IllegalArgumentException(mrl))
    }


    private def showPlayStatus(
        mrl: String,
        result: Try[Unit]) = {

        if ( result.isSuccess ) {
            println(s"Successfully started ${mrl}")
        } else {
            println(s"Failed to start ${mrl}")
        }
    }


    private def setupGui(): Future[Unit] = {

        val promise: Promise[Unit] = Promise()

        SwingUtilities.invokeLater(() => doSetupGui(promise))

        promise.future
    }


    private def doSetupGui(promise: Promise[Unit]): Unit = {

        val frame: JFrame = createMainFrame()
        frame.setContentPane(mediaPlayerComponent)
        frame.setVisible(true)

        promise.success(())
    }


    private def createMainFrame(): JFrame = {

        val frame: JFrame = new JFrame(APP_TITLE)
        frame.setBounds(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT)
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE)

        val onCloseListener: WindowListener = new WindowAdapter {
            override def windowClosing(event: WindowEvent): Unit = onExit()
        }

        frame.addWindowListener(onCloseListener)

        frame
    }


    private def onExit(): Unit = {

        mediaPlayerComponent.release()
        System.exit(0)
    }

}
