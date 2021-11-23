package inah.javaspring.controller;

public class MemberForm {
    // 회원가입 Form에 입력한 'name'이 등록된다.
    private String name;

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
