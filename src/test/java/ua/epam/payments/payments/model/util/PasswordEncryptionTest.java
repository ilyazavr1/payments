package ua.epam.payments.payments.model.util;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(Parameterized.class)
public class PasswordEncryptionTest {

    private PasswordEncryption encryptor = new PasswordEncryption();
    private String password;
    private String hashedPassword;


    public PasswordEncryptionTest(String password, String hashedPassword) {
        this.password = password;
        this.hashedPassword = hashedPassword;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Qwqe212Qeqw", "7248356d51d5cf7a62d77d27bc835fe1bd52ac7dd1ed9c18a175485b8ae7b35a95140519430ec666c7c2ddbad104b443d89e91fb1e09c8c9f246f1275f70895d7334a418e14f245cba59a5ec1adf4948"},
                {"Qwqe212Qeqw", "4ba91ea31c1f377d9e49349f38843566280f434b9b0eda7f9974380aa59bacf250f1a7515a1538f752e324e9d03262a7a74c6ef5754060aa85f3dea194eadae6b5fabd7d6f5f2f4a737f7f3b2384f6b9"},
                {"Qwqe212Qeqw", "27abf2539255f0cdb3883e38eec918abd6c1090eeca709ab56080edad1fccd8aa78fbabe26fe65d87d2bbcd47062e320f2c61dde17ae155ed72c827e3a459393cc49c1619c27a678e8831023ee3d50e0"},
                {"ASdsadSADsa21", "5c7b05348e35ff88855e817191e860e8f04ce21db913151c70c8c35b29b2e48e74873c5c9d201c395649c1a8230ec5cf0a44f7c07c697632e8dff649e005116747458347192a53ed80325360c0edc833"},
                {"ASdaswq221", "58f6ed19fac368f9edd051e388ef38ba340e7fd362ff6cce5361d3a6b94b752ab1c3a741df46edb1da7b05a2cb2c4c26a888a8adfbb71518b3f930e45e68fa42815c84c967bb2e8f85d83060c3c23a06"},
                {"232324BGFgfbgf", "d6d33380f14d827c2ee69de2fd994244794e530b74460bfcb2eee7f168969b95afcbbf41a92dc98c7b9c1754569808cbd28e4295417d6cb979596f5b3e401087b9a0d1637fa4e93ccca3982f492c9398"},
                {"QW2d2few3232", "5ebec8804535104df755f4c9387d8209eb8bd9deee3b8369cfcdbccaf11f1167f004b09ddcb3bee9ec9f01146ab99d47f35ee1153bc659ec99e32a4ee4d3eb4b96d36162a8e347b90439bc16025725cb"}

        });
    }

    @Test
    public void isPasswordCorrectWithGeneratedHash() {
        assertTrue(encryptor.isPasswordCorrect(password, encryptor.encrypt(password)));
    }


    @Test
    public void isPasswordCorrectWithPreparedHash() {
        assertTrue(encryptor.isPasswordCorrect(password, hashedPassword));
    }


}