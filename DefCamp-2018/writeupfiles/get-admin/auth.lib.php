<?php

/**
 * Authlib
 */
class AuthLib
{
    private $db;
    public function __construct(\PDO $db)
    {
        $this->db = $db;
    }

    /**
     * Secure authentication
     *
     * @param string $username
     * @param string $password
     * @return int|false
     */
    public function authenticate($username, $password)
    {
        $q = $this->db->prepare("SELECT * FROM users WHERE username = :username");
        $q->execute(['username' => $username]);
        if ($q->rowCount()) {
            $row = $q->fetch(\PDO::FETCH_ASSOC);

            // Valid username
            if ($this->verifyPassword($password, $row['password'])) {
                //return user id
                return $row['id'];
            }
        } 
        return false;
    }

    /**
     * Hash a password and compare against a known hash.
     * This function is safe against timing attacks.
     *
     * @param string $p plaintext password
     * @param string $h hash
     * @return boolean
     */
    public function verifyPassword($p, $h) {
        return password_verify($p, $h);
    }

    /**
     * Returns hash of a password using bcrypt.
     *
     * @param string $p1
     * @param string $p2
     * @return hash
     */
    public function hashPassword($p) {
        return password_hash($p, PASSWORD_DEFAULT);
    }

    public function updateUser($id, $username, $password, $email) {
        $q = $this->db->prepare("UPDATE users SET username = :username, password = :password, email= :email WHERE id = :id");
        $q->execute(['username' => $username,
                     'password' => $this->hashPassword($password),
                     'email' => $email,
                     'id' => $id]);
        return true;
    }

    public function createUser($username, $password, $email) {
        $q = $this->db->prepare("SELECT * FROM users WHERE username = :username OR email = :email");
        $q->execute(['username' => $username, 'email' => $email]);
        if ($q->rowCount()) {
            return "Duplicate email or username";
        }

        $q = $this->db->prepare("INSERT INTO users (username, password, email) VALUES(:username,:password,:email)");
        $q->execute(['username' => $username,
                     'password' => $this->hashPassword($password),
                     'email' => $email]);
        return true;
    } 
}