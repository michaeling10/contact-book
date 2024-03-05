package com.epam.rd.contactbook;

public class Contact{

    private String contactName;
    private String phoneNumber;
    private int emailCount = 0;
    private int socialCount = 0;
    private final ContactInfo[] emails = new ContactInfo[3];
    private final ContactInfo[] socials = new ContactInfo[5];

    public Contact(String contactName) {
        this.contactName = contactName;
    }

    public void rename(String newName) {
        if (newName != null && !newName.trim().isEmpty()) {
            this.contactName = newName;
        }
    }

    public Email addEmail(String localPart, String domain) {

        if (emailCount < 3){
            ContactInfo email = new Email(localPart, domain);
            emails[emailCount++] = email;
            return (Email) email;
        }
        return null;
    }


    public Email addEpamEmail(String firstname, String lastname) {
        if (emailCount < 3) {
            ContactInfo email = new Email(firstname + "_" + lastname, "epam.com") {
                @Override
                public String getTitle() {
                    return "Epam Email";
                }
            };
            emails[emailCount++] = email;
            return (Email) email;
        }
        return null;
    }

    public ContactInfo addPhoneNumber(int code, String number) {
        if (phoneNumber == null) {
            ContactInfo phone = new ContactInfo() {
                private final String title = "Tel";
                private final String value = "+" + code + " " + number;

                public String getTitle() {
                    return title;
                }

                public String getValue() {
                    return value;
                }
            };
            phoneNumber = phone.getValue();
            return phone;
        }
        return null;
    }

    public Social addTwitter(String twitterId) {
        if (socialCount < 5) {
            Social twitter = new Social("Twitter", twitterId);
            socials[socialCount++] = twitter;
            return twitter;
        }
        return null;
    }

    public Social addInstagram(String instagramId) {
        if (socialCount < 5) {
            Social instagram = new Social("Instagram", instagramId);
            socials[socialCount++] = instagram;
            return instagram;
        }
        return null;
    }

    public Social addSocialMedia(String title, String id) {
        if (socialCount < 5) {
            ContactInfo social = new Social(title, id);
            socials[socialCount++] = social;
            return (Social) social;
        }
        return null;
    }

    public ContactInfo[] getInfo() {
        ContactInfo[] info = new ContactInfo[1 + (phoneNumber != null ? 1 : 0) + emailCount + socialCount];
        int index = 0;
        info[index++] = new NameContactInfo();
        if (phoneNumber != null) {
            info[index++] = new ContactInfo() {
                public String getTitle() { return "Tel"; }
                public String getValue() { return phoneNumber; }
            };
        }
        System.arraycopy(emails, 0, info, index, emailCount);
        index += emailCount;
        System.arraycopy(socials, 0, info, index, socialCount);
        return info;
    }

    private class NameContactInfo implements ContactInfo{
        @Override
        public String getTitle() {
            return "Name";
        }

        @Override
        public String getValue() {
            return contactName;
        }
    }

    public static class Email implements ContactInfo{
        private final String value;

        public Email(String localPart, String domain) {
            this.value = localPart + "@" + domain;
        }

        @Override
        public String getTitle() {
            return "Email";
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    public static class Social implements ContactInfo{
        private final String title;
        private final String value;

        public Social(String title, String value) {
            this.title = title;
            this.value = value;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getValue() {
            return value;
        }
    }
}
