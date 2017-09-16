/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.gits;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javafx.concurrent.Worker;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import ken.mizoguch.webviewer.plugin.WebViewerPlugin;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.AddNoteCommand;
import org.eclipse.jgit.api.ApplyCommand;
import org.eclipse.jgit.api.ApplyResult;
import org.eclipse.jgit.api.ArchiveCommand;
import org.eclipse.jgit.api.BlameCommand;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CherryPickCommand;
import org.eclipse.jgit.api.CherryPickResult;
import org.eclipse.jgit.api.CleanCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.DeleteBranchCommand;
import org.eclipse.jgit.api.DeleteTagCommand;
import org.eclipse.jgit.api.DescribeCommand;
import org.eclipse.jgit.api.DiffCommand;
import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.GarbageCollectCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.ListNotesCommand;
import org.eclipse.jgit.api.ListTagCommand;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.NameRevCommand;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RebaseCommand;
import org.eclipse.jgit.api.RebaseResult;
import org.eclipse.jgit.api.ReflogCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.RemoteListCommand;
import org.eclipse.jgit.api.RemoteRemoveCommand;
import org.eclipse.jgit.api.RemoteSetUrlCommand;
import org.eclipse.jgit.api.RemoveNoteCommand;
import org.eclipse.jgit.api.RenameBranchCommand;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.RevertCommand;
import org.eclipse.jgit.api.RmCommand;
import org.eclipse.jgit.api.ShowNoteCommand;
import org.eclipse.jgit.api.StashApplyCommand;
import org.eclipse.jgit.api.StashCreateCommand;
import org.eclipse.jgit.api.StashDropCommand;
import org.eclipse.jgit.api.StashListCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.StatusCommand;
import org.eclipse.jgit.api.SubmoduleAddCommand;
import org.eclipse.jgit.api.SubmoduleInitCommand;
import org.eclipse.jgit.api.SubmoduleStatusCommand;
import org.eclipse.jgit.api.SubmoduleSyncCommand;
import org.eclipse.jgit.api.SubmoduleUpdateCommand;
import org.eclipse.jgit.api.TagCommand;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidMergeHeadsException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.notes.Note;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.submodule.SubmoduleStatus;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 *
 * @author mizoguch-ken
 */
public class Gits implements WebViewerPlugin {

    private WebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "gits";

    private Git git_;
    private final Gson gson_ = new Gson();

    public Gits() {
        git_ = null;
    }

    public Boolean open(String dir) throws IOException {
        if (dir != null) {
            return ((git_ = Git.open(Paths.get(dir).toFile())) != null);
        }
        return null;
    }

    public Boolean cloneRepository(String commandJson) throws GitAPIException {
        if (commandJson != null) {
            JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject;
                CloneCommand command = Git.cloneRepository();
                for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                    switch (entry.getKey()) {
                        case "credentials":
                            if (entry.getValue().isJsonObject()) {
                                jsonObject = entry.getValue().getAsJsonObject();
                                if (jsonObject.has("username") && jsonObject.has("password")) {
                                    command.setCredentialsProvider(
                                            new UsernamePasswordCredentialsProvider(
                                                    jsonObject.get("username").getAsString(),
                                                    jsonObject.get("password").getAsString()
                                            )
                                    );
                                }
                            }
                            break;
                        case "uri":
                            command.setURI(entry.getValue().getAsString());
                            break;
                        case "directory":
                            command.setDirectory(Paths.get(entry.getValue().getAsString()).toFile());
                            break;
                        case "gitDir":
                            command.setGitDir(Paths.get(entry.getValue().getAsString()).toFile());
                            break;
                        case "bare":
                            command.setBare(entry.getValue().getAsBoolean());
                            break;
                        case "remote":
                            command.setRemote(entry.getValue().getAsString());
                            break;
                        case "branch":
                            command.setBranch(entry.getValue().getAsString());
                            break;
                        case "monitor":
                            command.setProgressMonitor(null);
                            break;
                        case "cloneAllBranches":
                            command.setCloneAllBranches(entry.getValue().getAsBoolean());
                            break;
                        case "cloneSubmodules":
                            command.setCloneSubmodules(entry.getValue().getAsBoolean());
                            break;
                        case "noCheckout":
                            command.setNoCheckout(entry.getValue().getAsBoolean());
                            break;
                    }
                }
                return ((git_ = command.call()) != null);
            }
        }
        return null;
    }

    public RevCommit commit(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject;
                    CommitCommand command = git_.commit();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "message":
                                command.setMessage(entry.getValue().getAsString());
                                break;
                            case "allowEmpty":
                                command.setAllowEmpty(entry.getValue().getAsBoolean());
                                break;
                            case "committer":
                                if (entry.getValue().isJsonObject()) {
                                    jsonObject = entry.getValue().getAsJsonObject();
                                    if (jsonObject.has("name") && jsonObject.has("email")) {
                                        command.setCommitter(
                                                jsonObject.get("name").getAsString(),
                                                jsonObject.get("email").getAsString()
                                        );
                                    }
                                }
                                break;
                            case "author":
                                if (entry.getValue().isJsonObject()) {
                                    jsonObject = entry.getValue().getAsJsonObject();
                                    if (jsonObject.has("name") && jsonObject.has("email")) {
                                        command.setAuthor(
                                                jsonObject.get("name").getAsString(),
                                                jsonObject.get("email").getAsString()
                                        );
                                    }
                                }
                                break;
                            case "all":
                                command.setAll(entry.getValue().getAsBoolean());
                                break;
                            case "amend":
                                command.setAmend(entry.getValue().getAsBoolean());
                                break;
                            case "only":
                                command.setOnly(entry.getValue().getAsString());
                                break;
                            case "insertChangeId":
                                command.setInsertChangeId(entry.getValue().getAsBoolean());
                                break;
                            case "reflogComment":
                                command.setReflogComment(entry.getValue().getAsString());
                                break;
                            case "noVerify":
                                command.setNoVerify(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Iterable<RevCommit> log(String commandJson) throws IOException, GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    LogCommand command = git_.log();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "all":
                                command.all();
                                break;
                            case "path":
                                command.addPath(entry.getValue().getAsString());
                                break;
                            case "skip":
                                command.setSkip(entry.getValue().getAsInt());
                                break;
                            case "maxCount":
                                command.setMaxCount(entry.getValue().getAsInt());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public MergeResult merge(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    MergeCommand command = git_.merge();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "squash":
                                command.setSquash(entry.getValue().getAsBoolean());
                                break;
                            case "commit":
                                command.setCommit(entry.getValue().getAsBoolean());
                                break;
                            case "message":
                                command.setMessage(entry.getValue().getAsString());
                                break;
                            case "monitor":
                                command.setProgressMonitor(null);
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public PullResult pull(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject;
                    PullCommand command = git_.pull();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "credentials":
                                if (entry.getValue().isJsonObject()) {
                                    jsonObject = entry.getValue().getAsJsonObject();
                                    if (jsonObject.has("username") && jsonObject.has("password")) {
                                        command.setCredentialsProvider(
                                                new UsernamePasswordCredentialsProvider(
                                                        jsonObject.get("username").getAsString(),
                                                        jsonObject.get("password").getAsString()
                                                )
                                        );
                                    }
                                }
                                break;
                            case "monitor":
                                command.setProgressMonitor(null);
                                break;
                            case "useRebase":
                                command.setRebase(entry.getValue().getAsBoolean());
                                break;
                            case "remote":
                                command.setRemote(entry.getValue().getAsString());
                                break;
                            case "remoteBranchName":
                                command.setRemoteBranchName(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Ref branchCreate(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    CreateBranchCommand command = git_.branchCreate();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "name":
                                command.setName(entry.getValue().getAsString());
                                break;
                            case "force":
                                command.setForce(entry.getValue().getAsBoolean());
                                break;
                            case "startPoint":
                                command.setStartPoint(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public List<String> branchDelete(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    DeleteBranchCommand command = git_.branchDelete();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "force":
                                command.setForce(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public List<Ref> branchList(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    ListBranchCommand command = git_.branchList();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "containsCommitish":
                                command.setContains(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public List<Ref> tagList() throws GitAPIException {
        if (git_ != null) {
            ListTagCommand command = git_.tagList();
            return command.call();
        }
        return null;
    }

    public Ref branchRename(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    RenameBranchCommand command = git_.branchRename();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "newName":
                                command.setNewName(entry.getValue().getAsString());
                                break;
                            case "oldName":
                                command.setOldName(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public DirCache add(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    AddCommand command = git_.add();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "filepattern":
                                command.addFilepattern(entry.getValue().getAsString());
                                break;
                            case "update":
                                command.setUpdate(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Ref tag(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    TagCommand command = git_.tag();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "name":
                                command.setName(entry.getValue().getAsString());
                                break;
                            case "message":
                                command.setMessage(entry.getValue().getAsString());
                                break;
                            case "signed":
                                command.setSigned(entry.getValue().getAsBoolean());
                                break;
                            case "forceUpdate":
                                command.setForceUpdate(entry.getValue().getAsBoolean());
                                break;
                            case "annotated":
                                command.setAnnotated(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public FetchResult fetch(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject;
                    FetchCommand command = git_.fetch();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "credentials":
                                if (entry.getValue().isJsonObject()) {
                                    jsonObject = entry.getValue().getAsJsonObject();
                                    if (jsonObject.has("username") && jsonObject.has("password")) {
                                        command.setCredentialsProvider(
                                                new UsernamePasswordCredentialsProvider(
                                                        jsonObject.get("username").getAsString(),
                                                        jsonObject.get("password").getAsString()
                                                )
                                        );
                                    }
                                }
                                break;
                            case "remote":
                                command.setRemote(entry.getValue().getAsString());
                                break;
                            case "checkFetchedObjects":
                                command.setCheckFetchedObjects(entry.getValue().getAsBoolean());
                                break;
                            case "removeDeletedRefs":
                                command.setRemoveDeletedRefs(entry.getValue().getAsBoolean());
                                break;
                            case "monitor":
                                command.setProgressMonitor(null);
                                break;
                            case "dryRun":
                                command.setDryRun(entry.getValue().getAsBoolean());
                                break;
                            case "thin":
                                command.setThin(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Iterable<PushResult> push(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject;
                    PushCommand command = git_.push();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "credentials":
                                if (entry.getValue().isJsonObject()) {
                                    jsonObject = entry.getValue().getAsJsonObject();
                                    if (jsonObject.has("username") && jsonObject.has("password")) {
                                        command.setCredentialsProvider(
                                                new UsernamePasswordCredentialsProvider(
                                                        jsonObject.get("username").getAsString(),
                                                        jsonObject.get("password").getAsString()
                                                )
                                        );
                                    }
                                }
                                break;
                            case "remote":
                                command.setRemote(entry.getValue().getAsString());
                                break;
                            case "receivePack":
                                command.setReceivePack(entry.getValue().getAsString());
                                break;
                            case "monitor":
                                command.setProgressMonitor(null);
                                break;
                            case "add":
                                command.add(entry.getValue().getAsString());
                                break;
                            case "dryRun":
                                command.setDryRun(entry.getValue().getAsBoolean());
                                break;
                            case "thin":
                                command.setThin(entry.getValue().getAsBoolean());
                                break;
                            case "atomic":
                                command.setAtomic(entry.getValue().getAsBoolean());
                                break;
                            case "force":
                                command.setForce(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public CherryPickResult cherryPick(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    CherryPickCommand command = git_.cherryPick();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "ourCommitName":
                                command.setOurCommitName(entry.getValue().getAsString());
                                break;
                            case "prefix":
                                command.setReflogPrefix(entry.getValue().getAsString());
                                break;
                            case "noCommit":
                                command.setNoCommit(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public RevCommit revert(String commandJson) throws UnmergedPathsException, ConcurrentRefUpdateException, WrongRepositoryStateException, GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    RevertCommand command = git_.revert();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "ourCommitName":
                                command.setOurCommitName(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public RebaseResult rebase(String commandJson) throws RefNotFoundException, GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    RebaseCommand command = git_.rebase();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "upstream":
                                command.setUpstream(entry.getValue().getAsString());
                                break;
                            case "upstreamName":
                                command.setUpstreamName(entry.getValue().getAsString());
                                break;
                            case "monitor":
                                command.setProgressMonitor(null);
                                break;
                            case "preserve":
                                command.setPreserveMerges(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public DirCache rm(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    RmCommand command = git_.rm();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "filepattern":
                                command.addFilepattern(entry.getValue().getAsString());
                                break;
                            case "cached":
                                command.setCached(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Ref checkout(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    CheckoutCommand command = git_.checkout();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "path":
                                command.addPath(entry.getValue().getAsString());
                                break;
                            case "allPaths":
                                command.setAllPaths(entry.getValue().getAsBoolean());
                                break;
                            case "name":
                                command.setName(entry.getValue().getAsString());
                                break;
                            case "createBranch":
                                command.setCreateBranch(entry.getValue().getAsBoolean());
                                break;
                            case "orphan":
                                command.setOrphan(entry.getValue().getAsBoolean());
                                break;
                            case "force":
                                command.setForce(entry.getValue().getAsBoolean());
                                break;
                            case "startPoint":
                                command.setStartPoint(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Ref reset(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    ResetCommand command = git_.reset();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "ref":
                                command.setRef(entry.getValue().getAsString());
                                break;
                            case "path":
                                command.addPath(entry.getValue().getAsString());
                                break;
                            case "disable":
                                command.disableRefLog(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Status status(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    StatusCommand command = git_.status();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "path":
                                command.addPath(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public OutputStream archive(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    ArchiveCommand command = git_.archive();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "prefix":
                                command.setPrefix(entry.getValue().getAsString());
                                break;
                            case "filename":
                                command.setFilename(entry.getValue().getAsString());
                                break;
                            case "format":
                                command.setFormat(entry.getValue().getAsString());
                                break;
                            case "paths":
                                command.setPaths(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Note notesAdd(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    AddNoteCommand command = git_.notesAdd();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "message":
                                command.setMessage(entry.getValue().getAsString());
                                break;
                            case "notesRef":
                                command.setNotesRef(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Note notesRemove(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    RemoveNoteCommand command = git_.notesRemove();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "notesRef":
                                command.setNotesRef(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public List<Note> notesList(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    ListNotesCommand command = git_.notesList();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "notesRef":
                                command.setNotesRef(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Note notesShow(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    ShowNoteCommand command = git_.notesShow();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "notesRef":
                                command.setNotesRef(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Collection<Ref> lsRemote(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject;
                    LsRemoteCommand command = git_.lsRemote();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "credentials":
                                if (entry.getValue().isJsonObject()) {
                                    jsonObject = entry.getValue().getAsJsonObject();
                                    if (jsonObject.has("username") && jsonObject.has("password")) {
                                        command.setCredentialsProvider(
                                                new UsernamePasswordCredentialsProvider(
                                                        jsonObject.get("username").getAsString(),
                                                        jsonObject.get("password").getAsString()
                                                )
                                        );
                                    }
                                }
                                break;
                            case "remote":
                                command.setRemote(entry.getValue().getAsString());
                                break;
                            case "heads":
                                command.setHeads(entry.getValue().getAsBoolean());
                                break;
                            case "tags":
                                command.setTags(entry.getValue().getAsBoolean());
                                break;
                            case "uploadPack":
                                command.setUploadPack(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Set<String> clean(String commandJson) throws NoWorkTreeException, GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    CleanCommand command = git_.clean();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "dryRun":
                                command.setDryRun(entry.getValue().getAsBoolean());
                                break;
                            case "force":
                                command.setForce(entry.getValue().getAsBoolean());
                                break;
                            case "dirs":
                                command.setCleanDirectories(entry.getValue().getAsBoolean());
                                break;
                            case "ignore":
                                command.setIgnore(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public BlameResult blame(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    BlameCommand command = git_.blame();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "filePath":
                                command.setFilePath(entry.getValue().getAsString());
                                break;
                            case "follow":
                                command.setFollowFileRenames(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Collection<ReflogEntry> reflog(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    ReflogCommand command = git_.reflog();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "ref":
                                command.setRef(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public List<DiffEntry> diff(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    DiffCommand command = git_.diff();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "cached":
                                command.setCached(entry.getValue().getAsBoolean());
                                break;
                            case "showNameAndStatusOnly":
                                command.setShowNameAndStatusOnly(entry.getValue().getAsBoolean());
                                break;
                            case "contextLines":
                                command.setContextLines(entry.getValue().getAsInt());
                                break;
                            case "sourcePrefix":
                                command.setSourcePrefix(entry.getValue().getAsString());
                                break;
                            case "destinationPrefix":
                                command.setDestinationPrefix(entry.getValue().getAsString());
                                break;
                            case "monitor":
                                command.setProgressMonitor(null);
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public List<String> tagDelete(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    DeleteTagCommand command = git_.tagDelete();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "tags":
                                command.setTags(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Repository submoduleAdd(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject;
                    SubmoduleAddCommand command = git_.submoduleAdd();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "credentials":
                                if (entry.getValue().isJsonObject()) {
                                    jsonObject = entry.getValue().getAsJsonObject();
                                    if (jsonObject.has("username") && jsonObject.has("password")) {
                                        command.setCredentialsProvider(
                                                new UsernamePasswordCredentialsProvider(
                                                        jsonObject.get("username").getAsString(),
                                                        jsonObject.get("password").getAsString()
                                                )
                                        );
                                    }
                                }
                                break;
                            case "path":
                                command.setPath(entry.getValue().getAsString());
                                break;
                            case "uri":
                                command.setURI(entry.getValue().getAsString());
                                break;
                            case "monitor":
                                command.setProgressMonitor(null);
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Collection<String> submoduleInit(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    SubmoduleInitCommand command = git_.submoduleInit();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "path":
                                command.addPath(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Map<String, SubmoduleStatus> submoduleStatus(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    SubmoduleStatusCommand command = git_.submoduleStatus();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "path":
                                command.addPath(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Map<String, String> submoduleSync(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    SubmoduleSyncCommand command = git_.submoduleSync();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "path":
                                command.addPath(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Collection<String> submoduleUpdate(String commandJson) throws NoHeadException, ConcurrentRefUpdateException, CheckoutConflictException, InvalidMergeHeadsException, WrongRepositoryStateException, NoMessageException, RefNotFoundException, GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject;
                    SubmoduleUpdateCommand command = git_.submoduleUpdate();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "credentials":
                                if (entry.getValue().isJsonObject()) {
                                    jsonObject = entry.getValue().getAsJsonObject();
                                    if (jsonObject.has("username") && jsonObject.has("password")) {
                                        command.setCredentialsProvider(
                                                new UsernamePasswordCredentialsProvider(
                                                        jsonObject.get("username").getAsString(),
                                                        jsonObject.get("password").getAsString()
                                                )
                                        );
                                    }
                                }
                                break;
                            case "monitor":
                                command.setProgressMonitor(null);
                                break;
                            case "path":
                                command.addPath(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Collection<RevCommit> stashList(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    StashListCommand command = git_.stashList();
                    return command.call();
                }
            }
        }
        return null;
    }

    public RevCommit stashCreate(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    StashCreateCommand command = git_.stashCreate();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "indexMessage":
                                command.setIndexMessage(entry.getValue().getAsString());
                                break;
                            case "workingDirectoryMessage":
                                command.setWorkingDirectoryMessage(entry.getValue().getAsString());
                                break;
                            case "ref":
                                command.setRef(entry.getValue().getAsString());
                                break;
                            case "includeUntracked":
                                command.setIncludeUntracked(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public ObjectId stashApply(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    StashApplyCommand command = git_.stashApply();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "stashRef":
                                command.setStashRef(entry.getValue().getAsString());
                                break;
                            case "willIgnoreRepositoryState":
                                command.ignoreRepositoryState(entry.getValue().getAsBoolean());
                                break;
                            case "applyIndex":
                                command.setApplyIndex(entry.getValue().getAsBoolean());
                                break;
                            case "applyUntracked":
                                command.setApplyUntracked(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public ObjectId stashDrop(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    StashDropCommand command = git_.stashDrop();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "stashRef":
                                command.setStashRef(entry.getValue().getAsInt());
                                break;
                            case "all":
                                command.setAll(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public ApplyResult apply(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    ApplyCommand command = git_.apply();
                    return command.call();
                }
            }
        }
        return null;
    }

    public Properties gc(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    GarbageCollectCommand command = git_.gc();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "monitor":
                                command.setProgressMonitor(null);
                                break;
                            case "aggressive":
                                command.setAggressive(entry.getValue().getAsBoolean());
                                break;
                            case "preserveOldPacks":
                                command.setPreserveOldPacks(entry.getValue().getAsBoolean());
                                break;
                            case "prunePreserved":
                                command.setPrunePreserved(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public Map<ObjectId, String> nameRev(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    NameRevCommand command = git_.nameRev();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "prefix":
                                command.addPrefix(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public String describe(String commandJson) throws IOException, RefNotFoundException, GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    DescribeCommand command = git_.describe();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "rev":
                                command.setTarget(entry.getValue().getAsString());
                                break;
                            case "longDesc":
                                command.setLong(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public List<RemoteConfig> remoteList(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    RemoteListCommand command = git_.remoteList();
                    return command.call();
                }
            }
        }
        return null;
    }

    public RemoteConfig remoteAdd(String commandJson) throws GitAPIException, GitAPIException, GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    RemoteAddCommand command = git_.remoteAdd();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "name":
                                command.setName(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public RemoteConfig remoteRemove(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    RemoteRemoveCommand command = git_.remoteRemove();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "name":
                                command.setName(entry.getValue().getAsString());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    public RemoteConfig remoteSetUrl(String commandJson) throws GitAPIException {
        if (git_ != null) {
            if (commandJson != null) {
                JsonElement jsonElement = gson_.fromJson(commandJson, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    RemoteSetUrlCommand command = git_.remoteSetUrl();
                    for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                        switch (entry.getKey()) {
                            case "name":
                                command.setName(entry.getValue().getAsString());
                                break;
                            case "push":
                                command.setPush(entry.getValue().getAsBoolean());
                                break;
                        }
                    }
                    return command.call();
                }
            }
        }
        return null;
    }

    @Override
    public void initialize(WebViewerPlugin webViewer) {
        webViewer_ = webViewer;
    }

    @Override
    public String functionName() {
        return FUNCTION_NAME;
    }

    @Override
    public void state(Worker.State state) {
    }

    @Override
    public void close() {
        if (git_ != null) {
            git_.close();
            git_ = null;
        }
    }

    @Override
    public Stage stage() {
        return webViewer_.stage();
    }

    @Override
    public List<Image> icons() {
        return webViewer_.icons();
    }

    @Override
    public WebEngine webEngine() {
        return webViewer_.webEngine();
    }

    @Override
    public Path webPath() {
        return webViewer_.webPath();
    }

    @Override
    public void writeStackTrace(String name, Throwable throwable) {
        webViewer_.writeStackTrace(name, throwable);
    }

    @Override
    public void write(String name, String msg, boolean err) {
        webViewer_.write(name, msg, err);
    }
}
