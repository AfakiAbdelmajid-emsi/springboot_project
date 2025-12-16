export interface ProduitViewDTO {
  codePdt: number;
  nomPdt: string;
  prixPdt: number;
  qteStock: number;
  codeCmd: number;
}

export interface CreateProduct {
  codePdt?: number;
  nomPdt: string;
  descPdt: string;
  prixPdt: number;
  qtePdt: number;
}

export interface Commande {
  codeCmd: number;
  client: string;
  codePdt: number;
  qteCmd: number;
  dateCmd: string;
}

export interface LoginRequest {
  login: string;
  pass: string;
}

export interface LoginResponse {
  token?: string;
  error?: string;
}

export interface RegisterRequest {
  login: string;
  pass: string;
}

export interface RegisterResponse {
  message?: string;
  error?: string;
}


