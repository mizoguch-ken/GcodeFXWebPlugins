# GcodeFXWebPlugins Gits
## Overview 概要
 * Access Git.  
 Gitにアクセス
## Functions 関数
 * void licenses();
 * Boolean open(String dir);
 * Boolean cloneRepository(String commandJson);
 * RevCommit commit(String commandJson);
 * Iterable<RevCommit> log(String commandJson);
 * MergeResult merge(String commandJson);
 * PullResult pull(String commandJson);
 * Ref branchCreate(String commandJson);
 * List<String> branchDelete(String commandJson);
 * List<Ref> branchList(String commandJson);
 * List<Ref> tagList();
 * Ref branchRename(String commandJson);
 * DirCache add(String commandJson);
 * Ref tag(String commandJson);
 * FetchResult fetch(String commandJson);
 * Iterable<PushResult> push(String commandJson);
 * CherryPickResult cherryPick(String commandJson);
 * RevCommit revert(String commandJson);
 * RebaseResult rebase(String commandJson);
 * DirCache rm(String commandJson);
 * Ref checkout(String commandJson);
 * Ref reset(String commandJson);
 * Status status(String commandJson);
 * OutputStream archive(String commandJson);
 * Note notesAdd(String commandJson);
 * Note notesRemove(String commandJson);
 * List<Note> notesList(String commandJson);
 * Note notesShow(String commandJson);
 * Collection<Ref> lsRemote(String commandJson);
 * Set<String> clean(String commandJson);
 * BlameResult blame(String commandJson);
 * Collection<ReflogEntry> reflog(String commandJson);
 * List<DiffEntry> diff(String commandJson);
 * List<String> tagDelete(String commandJson);
 * Repository submoduleAdd(String commandJson);
 * Collection<String> submoduleInit(String commandJson);
 * Map<String, SubmoduleStatus> submoduleStatus(String commandJson);
 * Map<String, String> submoduleSync(String commandJson);
 * Collection<String> submoduleUpdate(String commandJson);
 * Collection<RevCommit> stashList(String commandJson);
 * RevCommit stashCreate(String commandJson);
 * ObjectId stashApply(String commandJson);
 * ObjectId stashDrop(String commandJson);
 * ApplyResult apply(String commandJson);
 * Properties gc(String commandJson);
 * Map<ObjectId, String> nameRev(String commandJson);
 * String describe(String commandJson);
 * List<RemoteConfig> remoteList(String commandJson);
 * RemoteConfig remoteAdd(String commandJson);
 * RemoteConfig remoteRemove(String commandJson);
 * RemoteConfig remoteSetUrl(String commandJson);
## Usage 使い方
 * Run with Javascript  
 Javascriptで実行する  
 
e.g.  
```
gits.open('/path/to/dir');
gits.pull({credentials: {username: 'name', password: 'pass'}});
```
