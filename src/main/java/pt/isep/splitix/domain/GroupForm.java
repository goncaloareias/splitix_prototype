package pt.isep.splitix.domain;

public class GroupForm {
    private String name;
    private String memberEmails;

    public GroupForm() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemberEmails() {
        return memberEmails;
    }

    public void setMemberEmails(String memberEmails) {
        this.memberEmails = memberEmails;
    }
}
