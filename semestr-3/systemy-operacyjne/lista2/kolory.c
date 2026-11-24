#include <stdio.h>

int main(void)
{
    int i;

    printf("16 podstawowych kolorow:\n");

    for (i = 0; i < 8; i++)
        printf("\033[%dmHello, World!\033[0m  (kod %d)\n", 30 + i, 30 + i);
    for (i = 0; i < 8; i++)
        printf("\033[%dmHello, World!\033[0m  (kod %d)\n", 90 + i, 90 + i);

    printf("\n256 kolorow:\n");

    for (i = 0; i < 256; i++) {
        printf("\033[38;5;%dmHello, World!\033[0m %3d   ", i, i);
        if (i % 4 == 3)
            printf("\n");
    }

    printf("\nTrue color (przyklad):\n");
    for (i = 0; i < 16; i++)
        printf("\033[38;2;%d;0;%dmHello, World!\033[0m  (R=%d, B=%d)\n",
               255 - i * 17, i * 17, 255 - i * 17, i * 17);

    return 0;
}
