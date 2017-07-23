package com.gunghi.tgwing.lolock.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by joyeongje on 2017. 7. 23..
 */

public class ResponseCaly {

    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("creator")
    @Expose
    private Creator creator;
    @SerializedName("end")
    @Expose
    private End end;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("htmlLink")
    @Expose
    private String htmlLink;
    @SerializedName("iCalUID")
    @Expose
    private String iCalUID;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("organizer")
    @Expose
    private Organizer organizer;
    @SerializedName("reminders")
    @Expose
    private Reminders reminders;
    @SerializedName("sequence")
    @Expose
    private Integer sequence;
    @SerializedName("start")
    @Expose
    private Start start;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("updated")
    @Expose
    private String updated;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseCaly() {
    }

    /**
     *
     * @param summary
     * @param reminders
     * @param etag
     * @param status
     * @param organizer
     * @param kind
     * @param creator
     * @param id
     * @param updated
     * @param created
     * @param start
     * @param sequence
     * @param iCalUID
     * @param end
     * @param htmlLink
     */
    public ResponseCaly(String created, Creator creator, End end, String etag, String htmlLink, String iCalUID, String id, String kind, Organizer organizer, Reminders reminders, Integer sequence, Start start, String status, String summary, String updated) {
        super();
        this.created = created;
        this.creator = creator;
        this.end = end;
        this.etag = etag;
        this.htmlLink = htmlLink;
        this.iCalUID = iCalUID;
        this.id = id;
        this.kind = kind;
        this.organizer = organizer;
        this.reminders = reminders;
        this.sequence = sequence;
        this.start = start;
        this.status = status;
        this.summary = summary;
        this.updated = updated;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public ResponseCaly withCreated(String created) {
        this.created = created;
        return this;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public ResponseCaly withCreator(Creator creator) {
        this.creator = creator;
        return this;
    }

    public End getEnd() {
        return end;
    }

    public void setEnd(End end) {
        this.end = end;
    }

    public ResponseCaly withEnd(End end) {
        this.end = end;
        return this;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public ResponseCaly withEtag(String etag) {
        this.etag = etag;
        return this;
    }

    public String getHtmlLink() {
        return htmlLink;
    }

    public void setHtmlLink(String htmlLink) {
        this.htmlLink = htmlLink;
    }

    public ResponseCaly withHtmlLink(String htmlLink) {
        this.htmlLink = htmlLink;
        return this;
    }

    public String getICalUID() {
        return iCalUID;
    }

    public void setICalUID(String iCalUID) {
        this.iCalUID = iCalUID;
    }

    public ResponseCaly withICalUID(String iCalUID) {
        this.iCalUID = iCalUID;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResponseCaly withId(String id) {
        this.id = id;
        return this;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public ResponseCaly withKind(String kind) {
        this.kind = kind;
        return this;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public ResponseCaly withOrganizer(Organizer organizer) {
        this.organizer = organizer;
        return this;
    }

    public Reminders getReminders() {
        return reminders;
    }

    public void setReminders(Reminders reminders) {
        this.reminders = reminders;
    }

    public ResponseCaly withReminders(Reminders reminders) {
        this.reminders = reminders;
        return this;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public ResponseCaly withSequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public Start getStart() {
        return start;
    }

    public void setStart(Start start) {
        this.start = start;
    }

    public ResponseCaly withStart(Start start) {
        this.start = start;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResponseCaly withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ResponseCaly withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public ResponseCaly withUpdated(String updated) {
        this.updated = updated;
        return this;
    }


    public class Start {

        @SerializedName("dateTime")
        @Expose
        private String dateTime;
        @SerializedName("timeZone")
        @Expose
        private String timeZone;

        /**
         * No args constructor for use in serialization
         *
         */
        public Start() {
        }

        /**
         *
         * @param dateTime
         * @param timeZone
         */
        public Start(String dateTime, String timeZone) {
            super();
            this.dateTime = dateTime;
            this.timeZone = timeZone;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public Start withDateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public Start withTimeZone(String timeZone) {
            this.timeZone = timeZone;
            return this;
        }

    }

    public class Reminders {

        @SerializedName("overrides")
        @Expose
        private List<Override> overrides = null;
        @SerializedName("useDefault")
        @Expose
        private Boolean useDefault;

        /**
         * No args constructor for use in serialization
         *
         */
    public Reminders() {
    }

    /**
     *
     * @param useDefault
     * @param overrides
     */
    public Reminders(List<Override> overrides, Boolean useDefault) {
        super();
        this.overrides = overrides;
        this.useDefault = useDefault;
    }

    public List<Override> getOverrides() {
        return overrides;
    }

    public void setOverrides(List<Override> overrides) {
        this.overrides = overrides;
    }

    public Reminders withOverrides(List<Override> overrides) {
        this.overrides = overrides;
        return this;
    }

    public Boolean getUseDefault() {
        return useDefault;
    }

    public void setUseDefault(Boolean useDefault) {
        this.useDefault = useDefault;
    }

    public Reminders withUseDefault(Boolean useDefault) {
        this.useDefault = useDefault;
        return this;
    }
    }

    public class Override {

        @SerializedName("method")
        @Expose
        private String method;
        @SerializedName("minutes")
        @Expose
        private Integer minutes;

        /**
         * No args constructor for use in serialization
         *
         */
        public Override() {
        }

        /**
         *
         * @param minutes
         * @param method
         */
        public Override(String method, Integer minutes) {
            super();
            this.method = method;
            this.minutes = minutes;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Override withMethod(String method) {
            this.method = method;
            return this;
        }

        public Integer getMinutes() {
            return minutes;
        }

        public void setMinutes(Integer minutes) {
            this.minutes = minutes;
        }

        public Override withMinutes(Integer minutes) {
            this.minutes = minutes;
            return this;
        }

    }

    public class Organizer {

        @SerializedName("displayName")
        @Expose
        private String displayName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("self")
        @Expose
        private Boolean self;

        /**
         * No args constructor for use in serialization
         *
         */
        public Organizer() {
        }

        /**
         *
         * @param email
         * @param self
         * @param displayName
         */
        public Organizer(String displayName, String email, Boolean self) {
            super();
            this.displayName = displayName;
            this.email = email;
            this.self = self;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public Organizer withDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Organizer withEmail(String email) {
            this.email = email;
            return this;
        }

        public Boolean getSelf() {
            return self;
        }

        public void setSelf(Boolean self) {
            this.self = self;
        }

        public Organizer withSelf(Boolean self) {
            this.self = self;
            return this;
        }

    }

    public class End {

        @SerializedName("dateTime")
        @Expose
        private String dateTime;
        @SerializedName("timeZone")
        @Expose
        private String timeZone;

        /**
         * No args constructor for use in serialization
         *
         */
        public End() {
        }

        /**
         *
         * @param dateTime
         * @param timeZone
         */
        public End(String dateTime, String timeZone) {
            super();
            this.dateTime = dateTime;
            this.timeZone = timeZone;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public End withDateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public End withTimeZone(String timeZone) {
            this.timeZone = timeZone;
            return this;
        }

    }

    public class Creator {

        @SerializedName("displayName")
        @Expose
        private String displayName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("self")
        @Expose
        private Boolean self;

        /**
         * No args constructor for use in serialization
         *
         */
        public Creator() {
        }

        /**
         *
         * @param email
         * @param self
         * @param displayName
         */
        public Creator(String displayName, String email, Boolean self) {
            super();
            this.displayName = displayName;
            this.email = email;
            this.self = self;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public Creator withDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Creator withEmail(String email) {
            this.email = email;
            return this;
        }

        public Boolean getSelf() {
            return self;
        }

        public void setSelf(Boolean self) {
            this.self = self;
        }

        public Creator withSelf(Boolean self) {
            this.self = self;
            return this;
        }

    }


}
