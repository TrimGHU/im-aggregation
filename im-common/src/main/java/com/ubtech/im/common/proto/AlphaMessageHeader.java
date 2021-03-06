package com.ubtech.im.common.proto;

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: AlphaMessageHeader.proto

public final class AlphaMessageHeader {
  private AlphaMessageHeader() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface AlphaMessageHeaderResponseOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required int32 commandId = 1;
    /**
     * <code>required int32 commandId = 1;</code>
     */
    boolean hasCommandId();
    /**
     * <code>required int32 commandId = 1;</code>
     */
    int getCommandId();

    // required string versionCode = 2;
    /**
     * <code>required string versionCode = 2;</code>
     */
    boolean hasVersionCode();
    /**
     * <code>required string versionCode = 2;</code>
     */
    java.lang.String getVersionCode();
    /**
     * <code>required string versionCode = 2;</code>
     */
    com.google.protobuf.ByteString
        getVersionCodeBytes();

    // required int64 sendSerial = 3;
    /**
     * <code>required int64 sendSerial = 3;</code>
     */
    boolean hasSendSerial();
    /**
     * <code>required int64 sendSerial = 3;</code>
     */
    long getSendSerial();

    // required int64 responseSerial = 4;
    /**
     * <code>required int64 responseSerial = 4;</code>
     */
    boolean hasResponseSerial();
    /**
     * <code>required int64 responseSerial = 4;</code>
     */
    long getResponseSerial();
  }
  /**
   * Protobuf type {@code AlphaMessageHeaderResponse}
   */
  public static final class AlphaMessageHeaderResponse extends
      com.google.protobuf.GeneratedMessage
      implements AlphaMessageHeaderResponseOrBuilder {
    // Use AlphaMessageHeaderResponse.newBuilder() to construct.
    private AlphaMessageHeaderResponse(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private AlphaMessageHeaderResponse(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final AlphaMessageHeaderResponse defaultInstance;
    public static AlphaMessageHeaderResponse getDefaultInstance() {
      return defaultInstance;
    }

    public AlphaMessageHeaderResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }

    @SuppressWarnings("unused")
    private AlphaMessageHeaderResponse(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              commandId_ = input.readInt32();
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              versionCode_ = input.readBytes();
              break;
            }
            case 24: {
              bitField0_ |= 0x00000004;
              sendSerial_ = input.readInt64();
              break;
            }
            case 32: {
              bitField0_ |= 0x00000008;
              responseSerial_ = input.readInt64();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return AlphaMessageHeader.internal_static_AlphaMessageHeaderResponse_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return AlphaMessageHeader.internal_static_AlphaMessageHeaderResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              AlphaMessageHeader.AlphaMessageHeaderResponse.class, AlphaMessageHeader.AlphaMessageHeaderResponse.Builder.class);
    }

    public static com.google.protobuf.Parser<AlphaMessageHeaderResponse> PARSER =
        new com.google.protobuf.AbstractParser<AlphaMessageHeaderResponse>() {
      public AlphaMessageHeaderResponse parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new AlphaMessageHeaderResponse(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<AlphaMessageHeaderResponse> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required int32 commandId = 1;
    public static final int COMMANDID_FIELD_NUMBER = 1;
    private int commandId_;
    /**
     * <code>required int32 commandId = 1;</code>
     */
    public boolean hasCommandId() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required int32 commandId = 1;</code>
     */
    public int getCommandId() {
      return commandId_;
    }

    // required string versionCode = 2;
    public static final int VERSIONCODE_FIELD_NUMBER = 2;
    private java.lang.Object versionCode_;
    /**
     * <code>required string versionCode = 2;</code>
     */
    public boolean hasVersionCode() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required string versionCode = 2;</code>
     */
    public java.lang.String getVersionCode() {
      java.lang.Object ref = versionCode_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          versionCode_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string versionCode = 2;</code>
     */
    public com.google.protobuf.ByteString
        getVersionCodeBytes() {
      java.lang.Object ref = versionCode_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        versionCode_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    // required int64 sendSerial = 3;
    public static final int SENDSERIAL_FIELD_NUMBER = 3;
    private long sendSerial_;
    /**
     * <code>required int64 sendSerial = 3;</code>
     */
    public boolean hasSendSerial() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>required int64 sendSerial = 3;</code>
     */
    public long getSendSerial() {
      return sendSerial_;
    }

    // required int64 responseSerial = 4;
    public static final int RESPONSESERIAL_FIELD_NUMBER = 4;
    private long responseSerial_;
    /**
     * <code>required int64 responseSerial = 4;</code>
     */
    public boolean hasResponseSerial() {
      return ((bitField0_ & 0x00000008) == 0x00000008);
    }
    /**
     * <code>required int64 responseSerial = 4;</code>
     */
    public long getResponseSerial() {
      return responseSerial_;
    }

    private void initFields() {
      commandId_ = 0;
      versionCode_ = "";
      sendSerial_ = 0L;
      responseSerial_ = 0L;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasCommandId()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasVersionCode()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasSendSerial()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasResponseSerial()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt32(1, commandId_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, getVersionCodeBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeInt64(3, sendSerial_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        output.writeInt64(4, responseSerial_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, commandId_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, getVersionCodeBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(3, sendSerial_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(4, responseSerial_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static AlphaMessageHeader.AlphaMessageHeaderResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AlphaMessageHeader.AlphaMessageHeaderResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AlphaMessageHeader.AlphaMessageHeaderResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AlphaMessageHeader.AlphaMessageHeaderResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AlphaMessageHeader.AlphaMessageHeaderResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static AlphaMessageHeader.AlphaMessageHeaderResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static AlphaMessageHeader.AlphaMessageHeaderResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static AlphaMessageHeader.AlphaMessageHeaderResponse parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static AlphaMessageHeader.AlphaMessageHeaderResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static AlphaMessageHeader.AlphaMessageHeaderResponse parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(AlphaMessageHeader.AlphaMessageHeaderResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code AlphaMessageHeaderResponse}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements AlphaMessageHeader.AlphaMessageHeaderResponseOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return AlphaMessageHeader.internal_static_AlphaMessageHeaderResponse_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return AlphaMessageHeader.internal_static_AlphaMessageHeaderResponse_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                AlphaMessageHeader.AlphaMessageHeaderResponse.class, AlphaMessageHeader.AlphaMessageHeaderResponse.Builder.class);
      }

      // Construct using AlphaMessageHeader.AlphaMessageHeaderResponse.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        commandId_ = 0;
        bitField0_ = (bitField0_ & ~0x00000001);
        versionCode_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        sendSerial_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000004);
        responseSerial_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000008);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return AlphaMessageHeader.internal_static_AlphaMessageHeaderResponse_descriptor;
      }

      public AlphaMessageHeader.AlphaMessageHeaderResponse getDefaultInstanceForType() {
        return AlphaMessageHeader.AlphaMessageHeaderResponse.getDefaultInstance();
      }

      public AlphaMessageHeader.AlphaMessageHeaderResponse build() {
        AlphaMessageHeader.AlphaMessageHeaderResponse result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public AlphaMessageHeader.AlphaMessageHeaderResponse buildPartial() {
        AlphaMessageHeader.AlphaMessageHeaderResponse result = new AlphaMessageHeader.AlphaMessageHeaderResponse(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.commandId_ = commandId_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.versionCode_ = versionCode_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.sendSerial_ = sendSerial_;
        if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
          to_bitField0_ |= 0x00000008;
        }
        result.responseSerial_ = responseSerial_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof AlphaMessageHeader.AlphaMessageHeaderResponse) {
          return mergeFrom((AlphaMessageHeader.AlphaMessageHeaderResponse)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(AlphaMessageHeader.AlphaMessageHeaderResponse other) {
        if (other == AlphaMessageHeader.AlphaMessageHeaderResponse.getDefaultInstance()) return this;
        if (other.hasCommandId()) {
          setCommandId(other.getCommandId());
        }
        if (other.hasVersionCode()) {
          bitField0_ |= 0x00000002;
          versionCode_ = other.versionCode_;
          onChanged();
        }
        if (other.hasSendSerial()) {
          setSendSerial(other.getSendSerial());
        }
        if (other.hasResponseSerial()) {
          setResponseSerial(other.getResponseSerial());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasCommandId()) {
          
          return false;
        }
        if (!hasVersionCode()) {
          
          return false;
        }
        if (!hasSendSerial()) {
          
          return false;
        }
        if (!hasResponseSerial()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        AlphaMessageHeader.AlphaMessageHeaderResponse parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (AlphaMessageHeader.AlphaMessageHeaderResponse) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required int32 commandId = 1;
      private int commandId_ ;
      /**
       * <code>required int32 commandId = 1;</code>
       */
      public boolean hasCommandId() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required int32 commandId = 1;</code>
       */
      public int getCommandId() {
        return commandId_;
      }
      /**
       * <code>required int32 commandId = 1;</code>
       */
      public Builder setCommandId(int value) {
        bitField0_ |= 0x00000001;
        commandId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int32 commandId = 1;</code>
       */
      public Builder clearCommandId() {
        bitField0_ = (bitField0_ & ~0x00000001);
        commandId_ = 0;
        onChanged();
        return this;
      }

      // required string versionCode = 2;
      private java.lang.Object versionCode_ = "";
      /**
       * <code>required string versionCode = 2;</code>
       */
      public boolean hasVersionCode() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required string versionCode = 2;</code>
       */
      public java.lang.String getVersionCode() {
        java.lang.Object ref = versionCode_;
        if (!(ref instanceof java.lang.String)) {
          java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          versionCode_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string versionCode = 2;</code>
       */
      public com.google.protobuf.ByteString
          getVersionCodeBytes() {
        java.lang.Object ref = versionCode_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          versionCode_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string versionCode = 2;</code>
       */
      public Builder setVersionCode(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        versionCode_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string versionCode = 2;</code>
       */
      public Builder clearVersionCode() {
        bitField0_ = (bitField0_ & ~0x00000002);
        versionCode_ = getDefaultInstance().getVersionCode();
        onChanged();
        return this;
      }
      /**
       * <code>required string versionCode = 2;</code>
       */
      public Builder setVersionCodeBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        versionCode_ = value;
        onChanged();
        return this;
      }

      // required int64 sendSerial = 3;
      private long sendSerial_ ;
      /**
       * <code>required int64 sendSerial = 3;</code>
       */
      public boolean hasSendSerial() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>required int64 sendSerial = 3;</code>
       */
      public long getSendSerial() {
        return sendSerial_;
      }
      /**
       * <code>required int64 sendSerial = 3;</code>
       */
      public Builder setSendSerial(long value) {
        bitField0_ |= 0x00000004;
        sendSerial_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int64 sendSerial = 3;</code>
       */
      public Builder clearSendSerial() {
        bitField0_ = (bitField0_ & ~0x00000004);
        sendSerial_ = 0L;
        onChanged();
        return this;
      }

      // required int64 responseSerial = 4;
      private long responseSerial_ ;
      /**
       * <code>required int64 responseSerial = 4;</code>
       */
      public boolean hasResponseSerial() {
        return ((bitField0_ & 0x00000008) == 0x00000008);
      }
      /**
       * <code>required int64 responseSerial = 4;</code>
       */
      public long getResponseSerial() {
        return responseSerial_;
      }
      /**
       * <code>required int64 responseSerial = 4;</code>
       */
      public Builder setResponseSerial(long value) {
        bitField0_ |= 0x00000008;
        responseSerial_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int64 responseSerial = 4;</code>
       */
      public Builder clearResponseSerial() {
        bitField0_ = (bitField0_ & ~0x00000008);
        responseSerial_ = 0L;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:AlphaMessageHeaderResponse)
    }

    static {
      defaultInstance = new AlphaMessageHeaderResponse(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:AlphaMessageHeaderResponse)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_AlphaMessageHeaderResponse_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_AlphaMessageHeaderResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\030AlphaMessageHeader.proto\"p\n\032AlphaMessa" +
      "geHeaderResponse\022\021\n\tcommandId\030\001 \002(\005\022\023\n\013v" +
      "ersionCode\030\002 \002(\t\022\022\n\nsendSerial\030\003 \002(\003\022\026\n\016" +
      "responseSerial\030\004 \002(\003"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_AlphaMessageHeaderResponse_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_AlphaMessageHeaderResponse_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_AlphaMessageHeaderResponse_descriptor,
              new java.lang.String[] { "CommandId", "VersionCode", "SendSerial", "ResponseSerial", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
