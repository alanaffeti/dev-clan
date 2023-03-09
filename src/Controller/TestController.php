<?php

namespace App\Controller;

use App\Entity\Constat;
use App\Form\ConstatType;
use App\Repository\ConstatRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use Doctrine\ORM\EntityManagerInterface;



class TestController extends AbstractController
{
    #[Route('/test', name: 'app_test')]
    public function index(): Response
    {
        return $this->render('test/index.html.twig', [
            'controller_name' => 'TestController',
        ]);
    }
  
    #[Route("addConstatJSON/new", name: "addconstatJSON")]
    public function addconstatJSON(Request $req,NormalizerInterface $Normalizer)
    {

        $em = $this->getDoctrine()->getManager();
        $constat = new constat();
        $constat->setIdClient($req->get('id_client'));
        $constat->setIdvehicule($req->get('id_vehicule'));
        
        $constat->setLieu($req->get('lieu'));

       
        $em->persist($constat);
        $em->flush();

        $jsonContent = $Normalizer->normalize($constat, 'json', ['groups' => 'reclamation']);
        return new Response(json_encode($jsonContent));
    }
}
