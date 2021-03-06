package me.assil.everexport.facades.evernote

import scala.scalajs.js
import scala.scalajs.js.Promise
import scala.scalajs.js.annotation.{JSImport, ScalaJSDefined}

@JSImport("evernote", "Types.Notebook")
@js.native
class Notebook(var guid: String, var name: String, var stack: String,
               var created: Long, var updated: Long) extends js.Object

@JSImport("evernote", "Types.Note")
@js.native
class Note(var guid: String, var title: String, var content: String, var created: Long,
           var updated: Long, var notebookGuid: String, var tagGuids: js.Array[String],
           var tagNames: js.Array[String], var resources: js.Array[Resource]) extends js.Object

@JSImport("evernote", "Types.Resource")
@js.native
class Resource(var guid: String, var noteGuid: String, var width: Int,
               var height: Int, var data: js.Array[Byte]) extends js.Object

@js.native
trait ClientParams extends js.Object {
  val consumerKey: js.UndefOr[String]
  val consumerSecret: js.UndefOr[String]
  val token: js.UndefOr[String]
  val sandbox: Boolean
  val china: Boolean
}

object ClientParams {
  def apply(consumerKey: js.UndefOr[String] = js.undefined, consumerSecret: js.UndefOr[String] = js.undefined,
            token: js.UndefOr[String] = js.undefined, sandbox: Boolean, china: Boolean = false): ClientParams = {
    if (consumerKey.isDefined && consumerSecret.isDefined)
      js.Dynamic.literal(
        consumerKey = consumerKey,
        consumerSecret = consumerSecret,
        sandbox = sandbox,
        china = china).asInstanceOf[ClientParams]
    else
      js.Dynamic.literal(
        token = token,
        sandbox = sandbox,
        china = china).asInstanceOf[ClientParams]
  }
}

@JSImport("evernote", "Client")
@js.native
class Client(val params: ClientParams) extends js.Object {

  // https://github.com/evernote/evernote-sdk-js/blob/a5f9eb20c30c148fc8fc3cd48016763e22e99431/src/client.js#L75
  def getRequestToken(callbackUrl: String, callback: js.Function3[String, String, String, Unit]): String = js.native

  // https://github.com/evernote/evernote-sdk-js/blob/a5f9eb20c30c148fc8fc3cd48016763e22e99431/src/client.js#L105
  def getNoteStore(noteStoreUrl: String = ""): NoteStore = js.native

  // https://github.com/evernote/evernote-sdk-js/blob/a5f9eb20c30c148fc8fc3cd48016763e22e99431/src/client.js#L86
  def getAccessToken(oauthToken: String, oauthTokenSecret: String, oauthVerifier: String,
                     callback: js.Function3[String, String, String, Unit]): String = js.native
}

@JSImport("evernote", "NoteStore")
@js.native
class NoteStore extends js.Object {
  def listNotebooks(): Promise[js.Array[Notebook]] = js.native
  def findNotesMetadata(filter: NoteFilter, offset: Int, maxNotes: Int,
                        spec: NotesMetadataResultSpec): Promise[NotesMetadataList] = js.native
  def getNote(guid: String, withContent: Boolean, withResourcesData: Boolean,
              withResourcesRecognition: Boolean, withResourcesAlternateData: Boolean): Promise[Note] = js.native
  def getNoteWithResultSpec(guid: String, resultSpec: NoteResultSpec): Promise[Note] = js.native
  def getNotebook(guid: String): Promise[Notebook] = js.native
}

@JSImport("evernote", "NoteStore.NotesMetadataResultSpec")
@js.native
class NotesMetadataResultSpec extends js.Object {
  var includeTitle: Boolean = js.native
  var includeUpdated: Boolean = js.native
}

@JSImport("evernote", "NoteStore.NoteResultSpec")
@js.native
class NoteResultSpec extends js.Object {
  var includeContent: Boolean = js.native
  var includeResourcesData: Boolean = js.native
  var includeResourcesRecognition: Boolean = js.native
  var includeResourcesAlternateData: Boolean = js.native
  var includeSharedNotes: Boolean = js.native
  var includeNoteAppDataValues: Boolean = js.native
  var includeResourceAppDataValues: Boolean = js.native
  var includeAccountLimits: Boolean = js.native
}

@JSImport("evernote", "NoteStore.NoteFilter")
@js.native
class NoteFilter extends js.Object {
  var notebookGuid: String = js.native
}

@JSImport("evernote", "NoteStore.NoteMetadata")
@js.native
class NoteMetadata extends js.Object {
  val guid: String = js.native
  val title: String = js.native
  val contentLength: Int = js.native
  val created: Long = js.native
  val updated: Long = js.native
  val deleted: Long = js.native
  val notebookGuid: String = js.native
  val tagGuids: Vector[String] = js.native
}

@JSImport("evernote", "NoteStore.NotesMetadataList")
@js.native
class NotesMetadataList extends js.Object {
  val totalNotes: Int = js.native
  val notes: js.Array[NoteMetadata] = js.native
  val updateCount: Int = js.native
  val startIndex: Int = js.native
}
