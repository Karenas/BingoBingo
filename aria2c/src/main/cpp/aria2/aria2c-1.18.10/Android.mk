LOCAL_PATH:= $(call my-dir)

#
# aria2c，主要依据 ./android-make 的工作输出所写
#

include $(CLEAR_VARS)

ifndef CXXSTL
	CXXSTL := /4.8
endif

LOCAL_CPP_EXTENSION := .cc

LOCAL_SRC_FILES:= \
	android/arm-ftruncate64.S \
	A2STR.cc \
	AbstractAuthResolver.cc \
	AbstractBtMessage.cc \
	AbstractCommand.cc \
	AbstractDiskWriter.cc \
	AbstractHttpServerResponseCommand.cc \
	AbstractOptionHandler.cc \
	AbstractProxyRequestCommand.cc \
	AbstractProxyResponseCommand.cc \
	AbstractSingleDiskAdaptor.cc \
	ActivePeerConnectionCommand.cc \
	AdaptiveFileAllocationIterator.cc \
	AdaptiveURISelector.cc \
	Adler32MessageDigestImpl.cc \
	AnnounceList.cc \
	AnnounceTier.cc \
	AsyncNameResolver.cc \
	AsyncNameResolverMan.cc \
	AuthConfig.cc \
	AuthConfigFactory.cc \
	AutoSaveCommand.cc \
	BackupIPv4ConnectCommand.cc \
	BencodeParser.cc \
	BitfieldMan.cc \
	BtAbortOutstandingRequestEvent.cc \
	BtAllowedFastMessage.cc \
	BtAnnounce.cc \
	BtBitfieldMessage.cc \
	BtBitfieldMessageValidator.cc \
	BtCancelMessage.cc \
	BtCheckIntegrityEntry.cc \
	BtChokeMessage.cc \
	BtDependency.cc \
	BtExtendedMessage.cc \
	BtFileAllocationEntry.cc \
	BtHandshakeMessage.cc \
	BtHandshakeMessageValidator.cc \
	BtHaveAllMessage.cc \
	BtHaveMessage.cc \
	BtHaveNoneMessage.cc \
	BtInterestedMessage.cc \
	BtKeepAliveMessage.cc \
	BtLeecherStateChoke.cc \
	BtNotInterestedMessage.cc \
	BtPieceMessage.cc \
	BtPieceMessageValidator.cc \
	BtPortMessage.cc \
	BtPostDownloadHandler.cc \
	BtRegistry.cc \
	BtRejectMessage.cc \
	BtRequestMessage.cc \
	BtRuntime.cc \
	BtSeederStateChoke.cc \
	BtSetup.cc \
	BtStopDownloadCommand.cc \
	BtSuggestPieceMessage.cc \
	BtUnchokeMessage.cc \
	BufferedFile.cc \
	ByteArrayDiskWriter.cc \
	CUIDCounter.cc \
	CheckIntegrityCommand.cc \
	CheckIntegrityDispatcherCommand.cc \
	CheckIntegrityEntry.cc \
	Checksum.cc \
	ChecksumCheckIntegrityEntry.cc \
	ChunkChecksum.cc \
	ChunkedDecodingStreamFilter.cc \
	ColorizedStream.cc \
	Command.cc \
	ConnectCommand.cc \
	ConsoleStatCalc.cc \
	ContentTypeRequestGroupCriteria.cc \
	Context.cc \
	ContextAttribute.cc \
	Cookie.cc \
	CookieStorage.cc \
	CreateRequestCommand.cc \
	DHTAbstractMessage.cc \
	DHTAbstractTask.cc \
	DHTAnnouncePeerMessage.cc \
	DHTAnnouncePeerReplyMessage.cc \
	DHTAutoSaveCommand.cc \
	DHTBucket.cc \
	DHTBucketRefreshCommand.cc \
	DHTBucketRefreshTask.cc \
	DHTBucketTree.cc \
	DHTConnectionImpl.cc \
	DHTEntryPointNameResolveCommand.cc \
	DHTFindNodeMessage.cc \
	DHTFindNodeReplyMessage.cc \
	DHTGetPeersCommand.cc \
	DHTGetPeersMessage.cc \
	DHTGetPeersReplyMessage.cc \
	DHTInteractionCommand.cc \
	DHTMessage.cc \
	DHTMessageDispatcherImpl.cc \
	DHTMessageEntry.cc \
	DHTMessageFactoryImpl.cc \
	DHTMessageReceiver.cc \
	DHTMessageTracker.cc \
	DHTMessageTrackerEntry.cc \
	DHTNode.cc \
	DHTNodeLookupEntry.cc \
	DHTNodeLookupTask.cc \
	DHTNodeLookupTaskCallback.cc \
	DHTPeerAnnounceCommand.cc \
	DHTPeerAnnounceEntry.cc \
	DHTPeerAnnounceStorage.cc \
	DHTPeerLookupTask.cc \
	DHTPeerLookupTaskCallback.cc \
	DHTPingMessage.cc \
	DHTPingReplyMessage.cc \
	DHTPingTask.cc \
	DHTQueryMessage.cc \
	DHTRegistry.cc \
	DHTReplaceNodeTask.cc \
	DHTResponseMessage.cc \
	DHTRoutingTable.cc \
	DHTRoutingTableDeserializer.cc \
	DHTRoutingTableSerializer.cc \
	DHTSetup.cc \
	DHTTaskExecutor.cc \
	DHTTaskFactoryImpl.cc \
	DHTTaskQueueImpl.cc \
	DHTTokenTracker.cc \
	DHTTokenUpdateCommand.cc \
	DHTUnknownMessage.cc \
	DNSCache.cc \
	DefaultAuthResolver.cc \
	DefaultBtAnnounce.cc \
	DefaultBtInteractive.cc \
	DefaultBtMessageDispatcher.cc \
	DefaultBtMessageFactory.cc \
	DefaultBtMessageReceiver.cc \
	DefaultBtProgressInfoFile.cc \
	DefaultBtRequestFactory.cc \
	DefaultDiskWriter.cc \
	DefaultDiskWriterFactory.cc \
	DefaultExtensionMessageFactory.cc \
	DefaultPeerStorage.cc \
	DefaultPieceStorage.cc \
	DefaultStreamPieceSelector.cc \
	DirectDiskAdaptor.cc \
	DiskAdaptor.cc \
	DlAbortEx.cc \
	DlRetryEx.cc \
	DownloadCommand.cc \
	DownloadContext.cc \
	DownloadEngine.cc \
	DownloadEngineFactory.cc \
	DownloadFailureException.cc \
	DownloadHandler.cc \
	DownloadHandlerConstants.cc \
	DownloadResult.cc \
	EpollEventPoll.cc \
	Exception.cc \
	ExpatXmlParser.cc \
	ExtensionMessageRegistry.cc \
	FatalException.cc \
	FeatureConfig.cc \
	FeedbackURISelector.cc \
	File.cc \
	FileAllocationCommand.cc \
	FileAllocationDispatcherCommand.cc \
	FileAllocationEntry.cc \
	FileEntry.cc \
	FillRequestGroupCommand.cc \
	FtpConnection.cc \
	FtpDownloadCommand.cc \
	FtpFinishDownloadCommand.cc \
	FtpInitiateConnectionCommand.cc \
	FtpNegotiationCommand.cc \
	FtpTunnelRequestCommand.cc \
	FtpTunnelResponseCommand.cc \
	GZipDecodingStreamFilter.cc \
	GZipEncoder.cc \
	GZipFile.cc \
	GeomStreamPieceSelector.cc \
	GroupId.cc \
	GrowSegment.cc \
	HandshakeExtensionMessage.cc \
	HaveEraseCommand.cc \
	HttpConnection.cc \
	HttpDownloadCommand.cc \
	HttpHeader.cc \
	HttpHeaderProcessor.cc \
	HttpInitiateConnectionCommand.cc \
	HttpListenCommand.cc \
	HttpProxyRequestCommand.cc \
	HttpProxyResponseCommand.cc \
	HttpRequest.cc \
	HttpRequestCommand.cc \
	HttpResponse.cc \
	HttpResponseCommand.cc \
	HttpServer.cc \
	HttpServerBodyCommand.cc \
	HttpServerCommand.cc \
	HttpServerResponseCommand.cc \
	HttpSkipResponseCommand.cc \
	IOFile.cc \
	IndexBtMessage.cc \
	IndexBtMessageValidator.cc \
	InitiateConnectionCommand.cc \
	InitiateConnectionCommandFactory.cc \
	InitiatorMSEHandshakeCommand.cc \
	InorderStreamPieceSelector.cc \
	InorderURISelector.cc \
	IteratableChecksumValidator.cc \
	IteratableChunkChecksumValidator.cc \
	JsonParser.cc \
	LibsslARC4Encryptor.cc \
	LibsslDHKeyExchange.cc \
	LibsslMessageDigestImpl.cc \
	LibsslTLSContext.cc \
	LibsslTLSSession.cc \
	LogFactory.cc \
	Logger.cc \
	LongestSequencePieceSelector.cc \
	LpdDispatchMessageCommand.cc \
	LpdMessage.cc \
	LpdMessageDispatcher.cc \
	LpdMessageReceiver.cc \
	LpdReceiveMessageCommand.cc \
	MSEHandshake.cc \
	MessageDigest.cc \
	MetadataInfo.cc \
	Metalink2RequestGroup.cc \
	MetalinkEntry.cc \
	MetalinkHttpEntry.cc \
	MetalinkMetaurl.cc \
	MetalinkParserController.cc \
	MetalinkParserState.cc \
	MetalinkParserStateImpl.cc \
	MetalinkParserStateMachine.cc \
	MetalinkParserStateV3Impl.cc \
	MetalinkParserStateV4Impl.cc \
	MetalinkPostDownloadHandler.cc \
	MetalinkResource.cc \
	Metalinker.cc \
	MultiDiskAdaptor.cc \
	MultiFileAllocationIterator.cc \
	MultiUrlRequestInfo.cc \
	NameResolveCommand.cc \
	NameResolver.cc \
	NetStat.cc \
	Netrc.cc \
	NetrcAuthResolver.cc \
	Notifier.cc \
	NsCookieParser.cc \
	NullSinkStreamFilter.cc \
	OpenedFileCounter.cc \
	Option.cc \
	OptionHandler.cc \
	OptionHandlerException.cc \
	OptionHandlerFactory.cc \
	OptionHandlerImpl.cc \
	OptionParser.cc \
	Peer.cc \
	PeerAbstractCommand.cc \
	PeerAddrEntry.cc \
	PeerChokeCommand.cc \
	PeerConnection.cc \
	PeerInitiateConnectionCommand.cc \
	PeerInteractionCommand.cc \
	PeerListenCommand.cc \
	PeerReceiveHandshakeCommand.cc \
	PeerSessionResource.cc \
	PeerStat.cc \
	Piece.cc \
	PieceHashCheckIntegrityEntry.cc \
	PieceStatMan.cc \
	PiecedSegment.cc \
	Platform.cc \
	PollEventPoll.cc \
	PriorityPieceSelector.cc \
	ProtocolDetector.cc \
	Range.cc \
	RangeBtMessage.cc \
	RangeBtMessageValidator.cc \
	RarestPieceSelector.cc \
	RealtimeCommand.cc \
	ReceiverMSEHandshakeCommand.cc \
	RecoverableException.cc \
	Request.cc \
	RequestGroup.cc \
	RequestGroupEntry.cc \
	RequestGroupMan.cc \
	RequestSlot.cc \
	RpcMethod.cc \
	RpcMethodFactory.cc \
	RpcMethodImpl.cc \
	RpcRequest.cc \
	RpcResponse.cc \
	SaveSessionCommand.cc \
	SeedCheckCommand.cc \
	SegmentMan.cc \
	SelectEventPoll.cc \
	ServerStat.cc \
	ServerStatMan.cc \
	SessionSerializer.cc \
	ShareRatioSeedCriteria.cc \
	Signature.cc \
	SimpleBtMessage.cc \
	SimpleRandomizer.cc \
	SingleFileAllocationIterator.cc \
	SinkStreamFilter.cc \
	SocketBuffer.cc \
	SocketCore.cc \
	SocketRecvBuffer.cc \
	SpeedCalc.cc \
	StreamCheckIntegrityEntry.cc \
	StreamFileAllocationEntry.cc \
	StreamFilter.cc \
	TimeA2.cc \
	TimeBasedCommand.cc \
	TimeSeedCriteria.cc \
	TimedHaltCommand.cc \
	TimerA2.cc \
	TorrentAttribute.cc \
	TrackerWatcherCommand.cc \
	TransferStat.cc \
	TruncFileAllocationIterator.cc \
	UDPTrackerClient.cc \
	UDPTrackerRequest.cc \
	URIResult.cc \
	UTMetadataDataExtensionMessage.cc \
	UTMetadataExtensionMessage.cc \
	UTMetadataPostDownloadHandler.cc \
	UTMetadataRejectExtensionMessage.cc \
	UTMetadataRequestExtensionMessage.cc \
	UTMetadataRequestFactory.cc \
	UTMetadataRequestTracker.cc \
	UTPexExtensionMessage.cc \
	UnionSeedCriteria.cc \
	UnknownLengthPieceStorage.cc \
	UnknownOptionException.cc \
	UriListParser.cc \
	ValueBase.cc \
	ValueBaseStructParserStateImpl.cc \
	ValueBaseStructParserStateMachine.cc \
	WatchProcessCommand.cc \
	WebSocketInteractionCommand.cc \
	WebSocketResponseCommand.cc \
	WebSocketSession.cc \
	WebSocketSessionMan.cc \
	WrDiskCache.cc \
	WrDiskCacheEntry.cc \
	XmlAttr.cc \
	XmlParser.cc \
	XmlRpcDiskWriter.cc \
	XmlRpcRequestParserController.cc \
	XmlRpcRequestParserStateImpl.cc \
	XmlRpcRequestParserStateMachine.cc \
	ZeroBtMessage.cc \
	base32.cc \
	bencode2.cc \
	bitfield.cc \
	bittorrent_helper.cc \
	console.cc \
	cookie_helper.cc \
	download_handlers.cc \
	download_helper.cc \
	fmt.cc \
	help_tags.cc \
	json.cc \
	magnet.cc \
	main.cc \
	message_digest_helper.cc \
	metalink_helper.cc \
	option_processing.cc \
	paramed_string.cc \
	prefs.cc \
	rpc_helper.cc \
	timegm.c \
	uri.cc \
	uri_split.c \
	util.cc \
	util_security.cc \
	version_usage.cc \
	wallclock.cc \
	android/android.c

#把 Makefile 中为 libtool 设置的 --silent 选项去掉以后，可在 ./android-make 执行过程中看到编译响应如：
#libtool: compile:  arm-linux-androideabi-g++ -DHAVE_CONFIG_H -I. -I.. -Wall -I../lib -I../intl -I./includes -I./includes -DLOCALEDIR=\"/usr/local/share/locale\" -DHAVE_CONFIG_H -I../deps/wslay/lib/includes -I../deps/wslay/lib/includes -DCARES_STATICLIB -I/home/leiyi/adt/usr/local/include -I/home/leiyi/adt/usr/local/include -I/home/leiyi/adt/toolchain/sysroot/usr/include -Os -g -pipe -std=c++11 -MT A2STR.lo -MD -MP -MF .deps/A2STR.Tpo -c A2STR.cc  -fPIC -DPIC -o .libs/A2STR.o

LOCAL_CFLAGS:= -Os -g
LOCAL_CPPFLAGS:= -DHAVE_CONFIG_H -Os -g -std=c++11 -DCARES_STATICLIB -fPIC -DPIC
LOCAL_CPP_FEATURES := exceptions

LOCAL_C_INCLUDES += $(LOCAL_PATH)/includes \
	$(LOCAL_PATH)/../cares \
	$(LOCAL_PATH)/../expat \
	$(LOCAL_PATH)/../wslay/lib/includes \
	$(LOCAL_PATH)/../../openssl/include \
	$(LOCAL_PATH)/../../sqlite

LOCAL_C_INCLUDES +=	$(NDK_ROOT)/sources/cxx-stl/gnu-libstdc++$(CXXSTL)/include \
			$(NDK_ROOT)/sources/cxx-stl/gnu-libstdc++$(CXXSTL)/libs/armeabi/include

#使得先编译 sqlite ssl crypto
LOCAL_SHARED_LIBRARIES := _libsqlite _libssl _libcrypto

LOCAL_LDFLAGS += -Os -g -pipe -std=c++11 -fPIE -pie

CPP_STATIC=$(NDK_ROOT)/sources/cxx-stl/gnu-libstdc++$(CXXSTL)/libs/$(APP_ABI)/libgnustl_static.a

LOCAL_LDLIBS += -lz \
	-L$(NDK_ROOT)/sources/cxx-stl/gnu-libstdc++$(CXXSTL)/libs/$(APP_ABI)/ -lstdc++ -lsupc++ \
	$(CPP_STATIC) \
	$(LOCAL_PATH)/../../../libs/$(APP_ABI)/libsqlite.so \
	$(LOCAL_PATH)/../../../libs/$(APP_ABI)/libcrypto.so \
	$(LOCAL_PATH)/../../../libs/$(APP_ABI)/libssl.so

LOCAL_STATIC_LIBRARIES := \
	libcares \
	libexpat \
	libwslay

#	libssl_static \
#	libcrypto_static \
	

#LOCAL_LDLIBS += -lm -lc -lz -llog

# LOCAL_ALLOW_UNDEFINED_SYMBOLS := true

LOCAL_MODULE:=aria2c

include $(BUILD_EXECUTABLE)
