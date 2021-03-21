package Java;

import org.junit.jupiter.api.Test;

class ProgramTest {

    @Test
    void main() {
        Program.main(new String[]{});

        Program.main(new String[]{"25.6"});

        Program.main(new String[]{"100", "50.75"});

        Program.main(new String[]{"qwe", "12f4"});
    }
}