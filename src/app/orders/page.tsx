'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { getProduits, createCommande, downloadFacture, downloadPDF } from '@/lib/api';
import { ProduitViewDTO, Commande } from '@/lib/types';
import Navigation from '@/components/Navigation';

export default function OrdersPage() {
  const router = useRouter();
  const [products, setProducts] = useState<ProduitViewDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [orderId, setOrderId] = useState<number | null>(null);
  const [formData, setFormData] = useState<Commande>({
    codeCmd: 0,
    client: '',
    codePdt: 0,
    qteCmd: 0,
    dateCmd: new Date().toISOString().split('T')[0],
  });

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      router.push('/login');
      return;
    }

    fetchProducts();
  }, [router]);

  const fetchProducts = async () => {
    try {
      setLoading(true);
      const data = await getProduits();
      setProducts(data);
      setError('');
    } catch (err) {
      setError('Failed to load products');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateOrder = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    try {
      const result = await createCommande(formData);
      setSuccess('Order created successfully!');
      setFormData({
        codeCmd: 0,
        client: '',
        codePdt: 0,
        qteCmd: 0,
        dateCmd: new Date().toISOString().split('T')[0],
      });
      setShowCreateForm(false);
      fetchProducts();
    } catch (err) {
      setError('Failed to create order');
      console.error(err);
    }
  };

  const handleDownloadInvoice = async (id: number) => {
    try {
      const blob = await downloadFacture(id);
      downloadPDF(blob, `facture-${id}.pdf`);
      setSuccess('Invoice downloaded successfully');
    } catch (err) {
      setError('Failed to download invoice');
      console.error(err);
    }
  };

  const selectedProduct = products.find((p) => p.codePdt === formData.codePdt);

  return (
    <div className="min-h-screen bg-gray-50 dark:bg-gray-900">
      <Navigation />
      <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        <div className="px-4 py-6 sm:px-0">
          <div className="mb-6 flex justify-between items-center">
            <div>
              <h1 className="text-3xl font-bold text-gray-900 dark:text-white">
                Orders Management
              </h1>
              <p className="mt-2 text-sm text-gray-600 dark:text-gray-400">
                Create orders and download invoices
              </p>
            </div>
            <button
              onClick={() => {
                setShowCreateForm(!showCreateForm);
                setError('');
                setSuccess('');
              }}
              className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
              Create Order
            </button>
          </div>

          {error && (
            <div className="mb-4 bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded">
              {error}
            </div>
          )}

          {success && (
            <div className="mb-4 bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded">
              {success}
            </div>
          )}

          {showCreateForm && (
            <div className="mb-6 bg-white dark:bg-gray-800 p-6 rounded-lg shadow">
              <h2 className="text-xl font-semibold mb-4 text-gray-900 dark:text-white">
                Create New Order
              </h2>
              <form onSubmit={handleCreateOrder} className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">
                    Command Code
                  </label>
                  <input
                    type="number"
                    required
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 dark:bg-gray-700 dark:border-gray-600 dark:text-white"
                    value={formData.codeCmd || ''}
                    onChange={(e) =>
                      setFormData({ ...formData, codeCmd: parseInt(e.target.value) || 0 })
                    }
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">
                    Client Name
                  </label>
                  <input
                    type="text"
                    required
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 dark:bg-gray-700 dark:border-gray-600 dark:text-white"
                    value={formData.client}
                    onChange={(e) =>
                      setFormData({ ...formData, client: e.target.value })
                    }
                  />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">
                    Product
                  </label>
                  <select
                    required
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 dark:bg-gray-700 dark:border-gray-600 dark:text-white"
                    value={formData.codePdt || ''}
                    onChange={(e) =>
                      setFormData({ ...formData, codePdt: parseInt(e.target.value) || 0 })
                    }
                  >
                    <option value="">Select a product</option>
                    {products.map((product) => (
                      <option key={product.codePdt} value={product.codePdt}>
                        {product.nomPdt} - ${product.prixPdt} (Stock: {product.qteStock})
                      </option>
                    ))}
                  </select>
                </div>
                {selectedProduct && (
                  <div className="bg-blue-50 dark:bg-blue-900 p-3 rounded">
                    <p className="text-sm text-blue-800 dark:text-blue-200">
                      Selected: {selectedProduct.nomPdt} - Price: ${selectedProduct.prixPdt} - Available Stock: {selectedProduct.qteStock}
                      {formData.codeCmd > 0 && (
                        <span className="ml-2 font-semibold">- Command Code: {formData.codeCmd}</span>
                      )}
                    </p>
                  </div>
                )}
                <div>
                  <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">
                    Quantity
                  </label>
                  <input
                    type="number"
                    required
                    min="1"
                    max={selectedProduct?.qteStock || 9999}
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 dark:bg-gray-700 dark:border-gray-600 dark:text-white"
                    value={formData.qteCmd || ''}
                    onChange={(e) =>
                      setFormData({ ...formData, qteCmd: parseInt(e.target.value) || 0 })
                    }
                  />
                  {selectedProduct && (
                    <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
                      Maximum available: {selectedProduct.qteStock}
                    </p>
                  )}
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">
                    Date
                  </label>
                  <input
                    type="date"
                    required
                    className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 dark:bg-gray-700 dark:border-gray-600 dark:text-white"
                    value={formData.dateCmd}
                    onChange={(e) =>
                      setFormData({ ...formData, dateCmd: e.target.value })
                    }
                  />
                </div>
                {selectedProduct && formData.qteCmd > 0 && (
                  <div className="bg-green-50 dark:bg-green-900 p-3 rounded">
                    <p className="text-sm font-semibold text-green-800 dark:text-green-200">
                      Total: ${(selectedProduct.prixPdt * formData.qteCmd).toFixed(2)}
                    </p>
                  </div>
                )}
                <div className="flex space-x-3">
                  <button
                    type="submit"
                    className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700"
                  >
                    Create Order
                  </button>
                  <button
                    type="button"
                    onClick={() => setShowCreateForm(false)}
                    className="px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400"
                  >
                    Cancel
                  </button>
                </div>
              </form>
            </div>
          )}

          <div className="mb-6 bg-white dark:bg-gray-800 p-6 rounded-lg shadow">
            <h2 className="text-xl font-semibold mb-4 text-gray-900 dark:text-white">
              Download Invoice
            </h2>
            <div className="flex space-x-3">
              <input
                type="number"
                placeholder="Enter code commande"
                className="px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 dark:bg-gray-700 dark:border-gray-600 dark:text-white"
                value={orderId || ''}
                onChange={(e) => setOrderId(parseInt(e.target.value) || null)}
              />
              <button
                onClick={() => orderId && handleDownloadInvoice(orderId)}
                disabled={!orderId}
                className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                Download PDF
              </button>
            </div>
          </div>

          {loading ? (
            <div className="text-center py-12">
              <div className="inline-block animate-spin rounded-full h-8 w-8 border-b-2 border-indigo-600"></div>
              <p className="mt-2 text-gray-600 dark:text-gray-400">Loading products...</p>
            </div>
          ) : (
            <div className="bg-white dark:bg-gray-800 shadow overflow-hidden sm:rounded-md">
              <div className="px-6 py-4 border-b border-gray-200 dark:border-gray-700">
                <h3 className="text-lg font-medium text-gray-900 dark:text-white">
                  Available Products for Order
                </h3>
              </div>
              {products.length === 0 ? (
                <div className="text-center py-12">
                  <p className="text-gray-500 dark:text-gray-400">No products available</p>
                </div>
              ) : (
                <ul className="divide-y divide-gray-200 dark:divide-gray-700">
                  {products.map((product) => (
                    <li key={product.codePdt} className="px-6 py-4">
                      <div className="flex items-center justify-between">
                        <div className="flex-1">
                          <div className="flex items-center">
                            <h3 className="text-lg font-medium text-gray-900 dark:text-white">
                              {product.nomPdt}
                            </h3>
                            <span className="ml-3 px-2 py-1 text-xs font-semibold rounded-full bg-indigo-100 text-indigo-800 dark:bg-indigo-900 dark:text-indigo-200">
                              Code: {product.codePdt}
                            </span>
                          </div>
                          <div className="mt-2 flex items-center space-x-4 text-sm text-gray-500 dark:text-gray-400">
                            <span className="font-semibold text-gray-900 dark:text-white">
                              Price: ${product.prixPdt}
                            </span>
                            <span
                              className={`font-semibold ${
                                product.qteStock > 0
                                  ? 'text-green-600 dark:text-green-400'
                                  : 'text-red-600 dark:text-red-400'
                              }`}
                            >
                              Stock: {product.qteStock}
                            </span>
                          </div>
                        </div>
                      </div>
                    </li>
                  ))}
                </ul>
              )}
            </div>
          )}

          <div className="mt-6">
            <button
              onClick={fetchProducts}
              className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
              Refresh
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

