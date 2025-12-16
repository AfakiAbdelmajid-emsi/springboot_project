import {
  ProduitViewDTO,
  CreateProduct,
  Commande,
  LoginRequest,
  LoginResponse,
  RegisterRequest,
  RegisterResponse,
} from './types';

const VENTE_BASE_URL = 'http://localhost:8080/api';

const getAuthToken = (): string | null => {
  if (typeof window !== 'undefined') {
    return localStorage.getItem('token');
  }
  return null;
};

const getHeaders = (): HeadersInit => {
  const token = getAuthToken();
  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
  };
  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }
  return headers;
};

export async function login(credentials: LoginRequest): Promise<LoginResponse> {
  const response = await fetch(`${VENTE_BASE_URL}/auth/login`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(credentials),
  });
  return response.json();
}

export async function register(
  credentials: RegisterRequest
): Promise<RegisterResponse> {
  const response = await fetch(`${VENTE_BASE_URL}/auth/register`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(credentials),
  });
  return response.json();
}

export async function getProduits(): Promise<ProduitViewDTO[]> {
  const headers = getHeaders();
  const token = getAuthToken();
  
  if (!token) {
    throw new Error('No authentication token found. Please login again.');
  }

  const response = await fetch(`${VENTE_BASE_URL}/vente/produits`, {
    method: 'GET',
    headers: headers,
  });
  
  if (response.status === 401) {
    throw new Error('Unauthorized. Please login again.');
  }
  
  if (!response.ok) {
    throw new Error('Failed to fetch products');
  }
  return response.json();
}

export async function createProduct(product: CreateProduct): Promise<string> {
  const headers = getHeaders();
  const token = getAuthToken();
  
  if (!token) {
    throw new Error('No authentication token found. Please login again.');
  }

  const productData: Omit<CreateProduct, 'codePdt'> = {
    nomPdt: product.nomPdt,
    descPdt: product.descPdt,
    prixPdt: product.prixPdt,
    qtePdt: product.qtePdt,
  };

  const response = await fetch(`${VENTE_BASE_URL}/vente/produits`, {
    method: 'POST',
    headers: headers,
    body: JSON.stringify(productData),
  });
  
  if (response.status === 401) {
    throw new Error('Unauthorized. Please login again.');
  }
  
  if (!response.ok) {
    const errorText = await response.text().catch(() => 'Unknown error');
    throw new Error(`Failed to create product: ${errorText}`);
  }
  return response.text();
}

export async function createCommande(commande: Commande): Promise<string> {
  const headers = getHeaders();
  const token = getAuthToken();
  
  if (!token) {
    throw new Error('No authentication token found. Please login again.');
  }

  const response = await fetch(`${VENTE_BASE_URL}/vente/commande`, {
    method: 'POST',
    headers: headers,
    body: JSON.stringify(commande),
  });
  
  if (response.status === 401) {
    throw new Error('Unauthorized. Please login again.');
  }
  
  if (!response.ok) {
    throw new Error('Failed to create command');
  }
  return response.text();
}

export async function downloadFacture(codeCmd: number): Promise<Blob> {
  const headers = getHeaders();
  const token = getAuthToken();
  
  if (!token) {
    throw new Error('No authentication token found. Please login again.');
  }

  const response = await fetch(`${VENTE_BASE_URL}/vente/facture/code/${codeCmd}`, {
    method: 'GET',
    headers: headers,
  });
  
  if (response.status === 401) {
    throw new Error('Unauthorized. Please login again.');
  }
  
  if (!response.ok) {
    throw new Error('Failed to download invoice');
  }
  return response.blob();
}

export function downloadPDF(blob: Blob, filename: string): void {
  const url = window.URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = filename;
  document.body.appendChild(a);
  a.click();
  window.URL.revokeObjectURL(url);
  document.body.removeChild(a);
}
