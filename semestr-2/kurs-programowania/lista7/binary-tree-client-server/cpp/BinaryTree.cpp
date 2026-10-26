#include <iostream>
#include <string>

using namespace std;

/**
 * Reprezentuje prostokat, pozwala na porownywanie po polu powierzchni.
 */
class Rectangle{
private:
	double width;
	double height;
	
	void init(double w,double h){
		width=w;
		height=h;
	}

public:
	Rectangle(){
		init(0,0);
	}
	
	Rectangle(double w,double h){
		init(w,h);
	}

	double getArea() const{
		return width*height;
	}

	bool operator<(const Rectangle& other) const{
		return getArea()<other.getArea();
	}
	
	bool operator>(const Rectangle& other) const{
		return getArea()>other.getArea();
	}
	
	bool operator==(const Rectangle& other) const{
		return getArea()==other.getArea();
	}

	friend ostream& operator<<(ostream& os,const Rectangle& r){
		os<<r.width<<"x"<<r.height<<"("<<r.getArea()<<")";
		return os;
	}
};

/**
 * Szablon drzewa poszukiwan binarnych (BST).
 * @tparam T Typ elementow (musi obslugiwac operatory porownania).
 */
template<typename T>
class BinaryTree{
private:
	struct Node{
		T key;
		Node* left;
		Node* right;
		
		void init(T k){
			key=k;
			left=nullptr;
			right=nullptr;
		}
		
		Node(T k){
			init(k);
		}
	};

	Node* root;

	void initTree(){
		root=nullptr;
	}

	Node* insertRec(Node* node,T key){
		if(node==nullptr){
			return new Node(key);
		}
		if(key<node->key){
			node->left=insertRec(node->left,key);
		}
		else if(key>node->key){
			node->right=insertRec(node->right,key);
		}
		return node;
	}

	bool searchRec(Node* node,T key) const{
		if(node==nullptr){
			return false;
		}
		if(key==node->key){
			return true;
		}
		else if(key<node->key){
			return searchRec(node->left,key);
		}
		else{
			return searchRec(node->right,key);
		}
	}

	Node* findMin(Node* node){
		while(node->left!=nullptr){
			node=node->left;
		}
		return node;
	}

	Node* deleteRec(Node* node,T key){
		if(node==nullptr){
			return nullptr;
		}
		if(key<node->key){
			node->left=deleteRec(node->left,key);
		}
		else if(key>node->key){
			node->right=deleteRec(node->right,key);
		}
		else{
			if(node->left==nullptr&&node->right==nullptr){
				delete node;
				return nullptr;
			}
			else if(node->left==nullptr){
				Node* temp=node->right;
				delete node;
				return temp;
			}
			else if(node->right==nullptr){
				Node* temp=node->left;
				delete node;
				return temp;
			}
			else{
				Node* minNode=findMin(node->right);
				node->key=minNode->key;
				node->right=deleteRec(node->right,minNode->key);
			}
		}
		return node;
	}

	void drawRec(Node* node,int space) const{
		if(node==nullptr){
			return;
		}
		int count=5;
		space+=count;
		
		drawRec(node->right,space);
		
		for(int i=count;i<space;i++){
			cout<<" ";
		}
		cout<<node->key<<"\n";
		
		drawRec(node->left,space);
	}

	void destroyTree(Node* node){
		if(node!=nullptr){
			destroyTree(node->left);
			destroyTree(node->right);
			delete node;
		}
	}

public:
	BinaryTree(){
		initTree();
	}

	~BinaryTree(){
		destroyTree(root);
	}

	/**
	 * Wstawia nowy element do drzewa.
	 * @param key Wartosc do wstawienia.
	 */
	void insert(T key){
		root=insertRec(root,key);
	}

	/**
	 * Szuka elementu w drzewie.
	 * @param key Szukana wartosc.
	 * @return true jesli istnieje, false w przeciwnym razie.
	 */
	bool search(T key) const{
		return searchRec(root,key);
	}

	/**
	 * Usuwa element z drzewa.
	 * @param key Wartosc do usuniecia.
	 */
	void remove(T key){
		root=deleteRec(root,key);
	}

	/**
	 * Rysuje drzewo w konsoli.
	 */
	void draw() const{
		drawRec(root,0);
		cout<<"\n";
	}
};

/**
 * Konwertuje tekst na int.
 */
int parseInt(const string& s){
	return stoi(s);
}

/**
 * Konwertuje tekst na double.
 */
double parseDouble(const string& s){
	return stod(s);
}

/**
 * Zwraca tekst bez zmian.
 */
string parseString(const string& s){
	return s;
}

/**
 * Tworzy obiekt Rectangle na podstawie tekstu.
 * @param input Tekst w formacie np. "4x5".
 * @return Nowy obiekt Rectangle.
 */
Rectangle parseRectangle(const string& input){
	size_t pos=input.find('x');
	if(pos!=string::npos){
		double w=stod(input.substr(0,pos));
		double h=stod(input.substr(pos+1));
		return Rectangle(w,h);
	}
	return Rectangle(0,0);
}

/**
 * Glowna petla obslugujaca komendy dla wybranego typu drzewa.
 * @param typeName Nazwa typu do wyswietlenia.
 * @param parse Wskaznik na funkcje przetwarzajaca wejscie.
 */
template<typename T>
void processGeneric(string typeName,T(*parse)(const string&)){
	BinaryTree<T> tree;
	string command;
	string valStr;

	cout<<"--- DRZEWO "<<typeName<<" ---\n";
	cout<<"Komendy: INSERT <wartosc>, SEARCH <wartosc>, DELETE <wartosc>, DRAW, EXIT\n";

	while(cin>>command){
		if(command=="INSERT"){
			cin>>valStr;
			tree.insert(parse(valStr));
			cout<<"Wstawiono.\n";
		}
		else if(command=="SEARCH"){
			cin>>valStr;
			if(tree.search(parse(valStr))) cout<<"Znaleziono.\n";
			else cout<<"Nie znaleziono.\n";
		}
		else if(command=="DELETE"){
			cin>>valStr;
			tree.remove(parse(valStr));
			cout<<"Usunieto.\n";
		}
		else if(command=="DRAW"){
			tree.draw();
		}
		else if(command=="EXIT"){
			break;
		}
		else{
			cout<<"Nieznana komenda.\n";
			cin.ignore(10000,'\n');
		}
	}
}

int main(){
	string type;
	cout<<"Wybierz typ drzewa (INTEGER, DOUBLE, STRING, RECTANGLE): ";
	cin>>type;

	if(type=="INTEGER"){
		processGeneric<int>("INTEGER",parseInt);
	}
	else if(type=="DOUBLE"){
		processGeneric<double>("DOUBLE",parseDouble);
	}
	else if(type=="STRING"){
		processGeneric<string>("STRING",parseString);
	}
	else if(type=="RECTANGLE"){
		processGeneric<Rectangle>("RECTANGLE",parseRectangle);
	}
	else{
		cout<<"Nieznany typ.\n";
	}
	return 0;
}