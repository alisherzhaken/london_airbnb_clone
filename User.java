
/**
 * User class stores the information about the user, i.e. his/her first name, surname
 *
 * @author Alisher Zhaken, Adrian Surani, Andrei Cinca and Mahsum Kocabey
 * @version 2020.03.26
 */
public class User
{
    private String name;
    private String surname;
    private String email;
    private String password;
    
    public User(String name, String surname, String email, String password)
    {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getSurname()
    {
        return surname;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public boolean equals(User user)
    {
        if (getEmail().equals(user.getEmail()))
        {
            return true;
        }
        return false;
    }
}
