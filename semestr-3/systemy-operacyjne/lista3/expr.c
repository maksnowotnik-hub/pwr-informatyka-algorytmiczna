#include "types.h"
#include "stat.h"
#include "user.h"

int
main(int argc, char *argv[])
{
  int a, b, wynik;

  if(argc != 4){
    printf(2, "zle argumenty, przyklad: expr 2 + 2\n");
    exit();
  }

  a = atoi(argv[1]);
  b = atoi(argv[3]);

  switch(argv[2][0]){
  case '+': wynik = a + b; break;
  case '-': wynik = a - b; break;
  case '*': wynik = a * b; break;
  case '/':
    if(b == 0){
      printf(2, "dzielenie przez zero\n");
      exit();
    }
    wynik = a / b;
    break;
  default:
    printf(2, "nieznany operator: %s\n", argv[2]);
    exit();
  }

  printf(1, "%d\n", wynik);
  exit();
}
